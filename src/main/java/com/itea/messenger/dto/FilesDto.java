package com.itea.messenger.dto;

import com.itea.messenger.type.FileTypes;
import lombok.Data;

@Data
public class FilesDto {
    private Long id;
    private String fileName;
    private FileTypes fileType;

    private String getFileExt() {
        int i = this.fileName.lastIndexOf('.');
        return i > 0 ? this.fileName.substring(i+1) : "";
    }

    public void setFileType() {
        String fileExt = getFileExt();
        this.fileType =  FileTypes.UNKNOWN;
        if (fileExt.compareToIgnoreCase("wav") == 0 || fileExt.compareToIgnoreCase("mp3") == 0) {
            this.fileType = FileTypes.SOUND;
            return;
        }
        if (fileExt.compareToIgnoreCase("jpg") == 0 || fileExt.compareToIgnoreCase("jpeg") == 0 ||
                fileExt.compareToIgnoreCase("bmp") == 0 || fileExt.compareToIgnoreCase("gif") == 0 ||
                fileExt.compareToIgnoreCase("png") == 0 ) {
            this.fileType = FileTypes.IMAGE;
            return;
        }
        if (fileExt.compareToIgnoreCase("doc") == 0 || fileExt.compareToIgnoreCase("docx") == 0 ||
                fileExt.compareToIgnoreCase("xls") == 0 || fileExt.compareToIgnoreCase("xlsx") == 0 ||
                fileExt.compareToIgnoreCase("rtf") == 0 ) {
            this.fileType = FileTypes.MSOFFICE_DOCUMENT;
            return;
        }
        if (fileExt.compareToIgnoreCase("pdf") == 0 ) {
            this.fileType = FileTypes.ACROBAT_DOCUMENT;
            return;
        }
    }

}
