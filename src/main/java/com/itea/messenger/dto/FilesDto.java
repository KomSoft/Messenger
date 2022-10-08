package com.itea.messenger.dto;

import com.itea.messenger.type.FileTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilesDto {
    private Long id;
    private String fileName;
    private FileTypes fileType;

    private static String getFileExt(String name) {
        if (name == null) {
            return "";
        }
        int i = name.lastIndexOf('.');
        return i > 0 ? name.substring(i + 1) : "";
    }

    public static FileTypes determineFileType(String name) {
        String fileExt = FilesDto.getFileExt(name);
        if (fileExt.equalsIgnoreCase("wav") || fileExt.equalsIgnoreCase("mp3")) {
            return FileTypes.SOUND;
        }
        if (fileExt.equalsIgnoreCase("jpg") || fileExt.equalsIgnoreCase("jpeg") ||
                fileExt.equalsIgnoreCase("bmp") || fileExt.equalsIgnoreCase("gif") ||
                fileExt.equalsIgnoreCase("png")) {
            return FileTypes.IMAGE;
        }
        if (fileExt.equalsIgnoreCase("doc") || fileExt.equalsIgnoreCase("docx") ||
                fileExt.equalsIgnoreCase("xls") || fileExt.equalsIgnoreCase("xlsx") ||
                fileExt.equalsIgnoreCase("rtf")) {
            return FileTypes.MSOFFICE_DOCUMENT;
        }
        if (fileExt.equalsIgnoreCase("pdf")) {
            return FileTypes.ACROBAT_DOCUMENT;
        }
        return FileTypes.UNKNOWN;
    }

    public void setFileType() {
        this.fileType = FilesDto.determineFileType(this.getFileName());
    }

    public void setFileType(FileTypes fileType) {
        this.setFileType();
//        or other method
    }

    public FilesDto (Long id, String fileName, FileTypes fileType) {
        this.id = id;
        this.fileName = fileName;
        this.setFileType(fileType);
    }

    public FilesDto (Long id, String fileName) {
        this.id = id;
        this.fileName = fileName;
        this.setFileType();
    }

}