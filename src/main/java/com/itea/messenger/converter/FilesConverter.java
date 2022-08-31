package com.itea.messenger.converter;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.entity.Files;
import org.springframework.stereotype.Component;


@Component
public class FilesConverter {
    public Files fileEntityFromDto(FilesDto filesDto){
        Files file = new Files();
        file.setId(filesDto.getId());
        file.setFileType(filesDto.getFileType());
        file.setFileName(filesDto.getFileName());
        return file;
    }
    public FilesDto dtoFromFileEntity(Files files){
        FilesDto fileDto = new FilesDto();
        fileDto.setId(files.getId());
        fileDto.setFileType(files.getFileType());
        fileDto.setFileName(files.getFileName());
        return fileDto;
    }
}


