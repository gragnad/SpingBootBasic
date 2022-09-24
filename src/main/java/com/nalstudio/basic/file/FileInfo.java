package com.nalstudio.basic.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileInfo {
    private String fileName;
    private String savedName;
    private String fileDownloadUri;
    private String fileType;
    private String filePath;
    private long size;
}
