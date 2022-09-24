package com.nalstudio.basic.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssociationFileInfo {
    private String file_path;
    private String file_name;
    private String file_original;
    private String url_path;
    private String file_type;
}
