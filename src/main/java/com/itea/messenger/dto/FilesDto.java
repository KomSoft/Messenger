package com.itea.messenger.dto;

import com.itea.messenger.enums.fileType.FileTypes;
import lombok.Data;

@Data
public class FilesDto {
    private Long id;
    private String file;
    private FileTypes fileType;
}
