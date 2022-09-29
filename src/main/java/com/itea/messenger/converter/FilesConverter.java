package com.itea.messenger.converter;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.entity.Files;
import com.itea.messenger.exception.ValidationException;
import org.springframework.stereotype.Component;
import static java.util.Objects.isNull;

@Component
public class FilesConverter {

    private void validateFileDto(FilesDto filesDto) throws ValidationException {
        if (isNull(filesDto)) {
            throw new ValidationException("[FileDto] object is null");
        }
        if (isNull(filesDto.getFileName()) || filesDto.getFileName().isEmpty()) {
            throw new ValidationException("[FileDto] name is null or empty");
        }
        filesDto.setFileType();
    }

    public FilesDto fileEntityToDto(Files file) {
        return new FilesDto(file.getId(), file.getFileName(), file.getFileType());
/*
        fileDto.setId(file.getId());
        fileDto.setFileName(file.getFileName());
        fileDto.setFileType(file.getFileType());
*/
    }

    public Files fileEntityFromDto(FilesDto fileDto) throws ValidationException {
        validateFileDto(fileDto);
        Files file = new Files(fileDto.getFileName());
        file.setId(fileDto.getId());
        file.setFileType(fileDto.getFileType());
        return file;
    }

}


