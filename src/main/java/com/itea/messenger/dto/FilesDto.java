package com.itea.messenger.dto;

import com.itea.messenger.type.FileTypes;
import lombok.Data;

@Data
public class FilesDto {
    private Long id;
    private String fileName;
    private FileTypes fileType;
}
