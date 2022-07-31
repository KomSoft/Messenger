package com.itea.messenger.converter;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.entity.Files;
import org.springframework.stereotype.Component;


@Component
public class FilesConverter {
    public Files fileEntityFromDto(FilesDto filesDto){
        Files files = new Files();
        files.setId(filesDto.getId());
        files.setFileType(filesDto.getFileType());
        files.setFile(filesDto.getFile());
        return files;
    }
    public FilesDto dtoFromFileEntity(Files files){
        FilesDto filesDto = new FilesDto();
        filesDto.setId(files.getId());
        filesDto.setFileType(files.getFileType());
        filesDto.setFile(files.getFile());
        return filesDto;
    }
}


