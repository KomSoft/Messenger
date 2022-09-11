package com.itea.messenger.converter;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.entity.Files;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.type.FileTypes;
import org.springframework.stereotype.Component;
import static java.util.Objects.isNull;

@Component
public class FilesConverter {

    public FilesDto fileEntityToDto(Files file) {
        FilesDto fileDto = new FilesDto();
        fileDto.setId(file.getId());
        fileDto.setFileName(file.getFileName());
        fileDto.setFileType(file.getFileType());
        return fileDto;
    }

    public Files fileEntityFromDto(FilesDto fileDto) throws ValidationException {
        validateFileDto(fileDto);
        Files file = new Files();
        file.setId(fileDto.getId());
        file.setFileName(fileDto.getFileName());
        file.setFileType(fileDto.getFileType());
        return file;
    }

    private void validateFileDto(FilesDto filesDto) throws ValidationException {
        if (isNull(filesDto)) {
            throw new ValidationException("Object file is null");
        }
        if (isNull(filesDto.getFileName()) || filesDto.getFileName().isEmpty()) {
            throw new ValidationException("[File] name is null or empty");
        }
        if (isNull(filesDto.getFileType()) || filesDto.getFileType() == FileTypes.UNKNOWN) {
            filesDto.setFileType();
        }
    }

}


