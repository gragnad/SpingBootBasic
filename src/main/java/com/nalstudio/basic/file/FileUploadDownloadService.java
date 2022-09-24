package com.nalstudio.basic.file;

import com.nalstudio.basic.exception.FileDownloadException;
import com.nalstudio.basic.exception.FileUploadException;
import com.nalstudio.basic.system.dao.SystemTransactionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FileUploadDownloadService {
    private final SystemTransactionDao systemTransactionDao;

    private final FileUploadProperties prop;

    public Boolean insertAssociationAttachment(AssociationFileInfo info, int idx) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idx", idx);
        params.put("file_path", info.getFile_path());
        params.put("file_name", info.getFile_name());
        params.put("file_original", info.getFile_original());
        params.put("url_path", info.getUrl_path());
        params.put("file_type", info.getFile_type());
        boolean isInserted = systemTransactionDao.insert("",params) > 0;
        return isInserted;
    }

    public String saveCompanyLogoImage(MultipartFile file) throws IOException {
        String uploadPath = prop.getUploadDir();
        uploadPath += "/company/logo";
        String relativePath = createPath(uploadPath);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
        }
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String savedName = convertStringToHex(fileName) + "." + extension;

        byte[] bytes = file.getBytes();
        File fileOut = new File(relativePath, savedName);

        if (fileOut.exists()) {
            throw new FileUploadException("파일이 이미 존재합니다. " + fileName);
        }
        FileOutputStream fileoutputStream = new FileOutputStream(fileOut);
        fileoutputStream.write(bytes);
        fileoutputStream.close();

        setOwnerAccess(fileOut);

        System.out.println(savedName);
//	        return fileOut.getAbsolutePath();
        uploadPath = uploadPath.replace("home/tomcat/data","/");
        return "" + uploadPath + "/" + savedName;
    }

    public boolean updateCompanyLogoImage(String logo_img,String buid) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("logo_img", logo_img);
        paramMap.put("buid", buid);
        return systemTransactionDao.insert("",paramMap) > 0;
    }

    public List<AssociationFileInfo> saveAssociationFile(List<MultipartFile> files) throws IOException {
        String uploadPath = prop.getUploadDir();
        System.out.println("Association file upload uploadPath : " + uploadPath);
        uploadPath += "/association";
        List<AssociationFileInfo> fileInfoList = new ArrayList<>();

        for (MultipartFile file : files) {
            AssociationFileInfo fileInfo = new AssociationFileInfo(null, null, null, null, null);


            String relativePath = pathCreate(uploadPath);
            String absolutePath = uploadPath + relativePath;

            fileInfo.setFile_path(absolutePath);
//	            data/upload/
            fileInfo.setUrl_path(absolutePath.replace("/home/tomcat/data", ""));//save path
            System.out.println("Association file upload absolutePath : " + absolutePath);

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
            }
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String savedName = convertStringToHex(fileName) + "." + extension;
            fileInfo.setFile_name(savedName);
            fileInfo.setFile_original(fileName);
            fileInfo.setFile_type(extension);
            byte[] bytes = file.getBytes();
            File fileOut = new File(absolutePath, savedName);

            if (fileOut.exists()) {
                throw new FileUploadException("파일이 이미 존재합니다. " + fileName);
            }
            FileOutputStream fileoutputStream = new FileOutputStream(fileOut);
            fileoutputStream.write(bytes);
            fileoutputStream.close();

            setOwnerAccess(fileOut);
            System.out.println(absolutePath);
            System.out.println(savedName);
            fileInfoList.add(fileInfo);
        }


        return fileInfoList;
    }

    public FileInfo storeFile(MultipartFile file) throws IOException {
        String uploadPath = prop.getUploadDir();
        String relativePath = pathCreate(uploadPath);
        System.out.println(relativePath);
        String absolutePath = uploadPath + relativePath;
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String savedName = convertStringToHex(fileName) + "." + extension;

        byte[] bytes = file.getBytes();
        File fileOut = new File(absolutePath, savedName);

        if (fileOut.exists()) {
            throw new FileUploadException("파일이 이미 존재합니다. " + fileName);
        }
        FileOutputStream fileoutputStream = new FileOutputStream(fileOut);
        fileoutputStream.write(bytes);
        fileoutputStream.close();

        setOwnerAccess(fileOut);
        System.out.println(absolutePath);
        System.out.println(savedName);
        return FileInfo.builder()
                .fileName(fileName)
                .savedName(savedName)
                .fileType(file.getContentType())
                .size(file.getSize())
                .filePath(relativePath)
                .fileDownloadUri("" + relativePath + "/" + savedName)
                .build();

	/*        Path fileLocation = Paths.get(prop.getUploadDir())
	                .toAbsolutePath().normalize();
	        try {
	            Files.createDirectories(fileLocation);
	        }catch(Exception e) {
	            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
	        }

	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // 파일명에 부적합 문자가 있는지 확인한다.
	            if(fileName.contains("..")){
	                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
	            }

	            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy_MM_dd_HH_mm_ss_");
	            Calendar time = Calendar.getInstance();
	            String timeForamat = format1.format(time.getTime());
	            String savedName = timeForamat + fileName;
	            Path targetLocation = fileLocation.resolve(savedName);

	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

	            return FileInfo.builder()
	                    .fileName(fileName)
	                    .savedName(savedName)
	                    .fileType(file.getContentType())
	                    .size(file.getSize())
	                    .build();
	        }catch(Exception e) {
	            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
	        }*/
    }

    /**
     * 파일 경로 생성
     *
     * @param ""
     * @return
     */
    private String pathCreate(String uploadPath) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat makeYear = new SimpleDateFormat("yyyy");
        String year = makeYear.format(cal.getTime());

        SimpleDateFormat makeMonth = new SimpleDateFormat("MM");
        String month = makeMonth.format(cal.getTime());

        uploadPath = uploadPath + "/" + year;

        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
            setOwnerAccess(file);
        }
        uploadPath = uploadPath + "/" + month;
        file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
            setOwnerAccess(file);
        }

        return "/" + year + "/" + month;
    }

    private String createPath(String uploadPath) {
        File file = new File(uploadPath);

        if (!file.isDirectory()) {
            file.mkdirs();
            setOwnerAccess(file);
        }
        return file.getAbsolutePath();
    }

    private void setOwnerAccess(File path) {
        path.setExecutable(true, false);
        path.setWritable(true, false);
        path.setReadable(true, false);
    }

    public Resource loadFileAsResource(String fileName) {
        Path fileLocation = Paths.get(prop.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileLocation);
        } catch (Exception e) {
            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }

        try {
            Path filePath = fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        }
    }

    /**
     * 퍼미션 설정
     *
     * @param file
     * @throws IOException
     */
    public void setPermission(File file) throws IOException {
        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);

        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);

        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);

        Files.setPosixFilePermissions(file.toPath(), perms);
    }

    /**
     * String  to Hex
     *
     * @param str
     * @return
     */
    public String convertStringToHex(String str) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar time = Calendar.getInstance();
        String timeFormat = format1.format(time.getTime());

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return timeFormat + hex.toString();
    }
}
