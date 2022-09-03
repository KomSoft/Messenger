package com.itea.messenger.service;

import com.itea.messenger.converter.FilesConverter;
import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.entity.Files;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.FilesRepository;
import com.itea.messenger.type.FileTypes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultFilesService implements FilesService {
    private final FilesRepository filesRepository;
    private final FilesConverter filesConverter;

    private void validateFileDto(FilesDto filesDto) throws ValidationException {
        if (isNull(filesDto)){
            throw new ValidationException("Object file is null");
            }
        if (isNull(filesDto.getFileName()) || filesDto.getFileName().isEmpty()) {
            throw new ValidationException("File name is null or empty");
        }
        if (isNull(filesDto.getFileType()) || filesDto.getFileType() == FileTypes.UNKNOWN) {
            filesDto.setFileType();
        }
    }

    @Override
    public FilesDto saveFile(FilesDto filesDto) throws ValidationException {
        validateFileDto(filesDto);
        Files file = filesRepository.save(filesConverter.fileEntityFromDto(filesDto));
        return filesConverter.dtoFromFileEntity(file);
    }

    @Override
    public FilesDto findById(Long id) throws ValidationException {
        Files file = filesRepository.findById(id).orElseThrow(() -> new ValidationException("No file is this id:" + id));
        return filesConverter.dtoFromFileEntity(file);
    }

    @Override
    public List<FilesDto> findAll() {
        List<FilesDto> dtoList = new ArrayList<>();
        List<Files> list = filesRepository.findAll();
        for (Files file: list) {
            dtoList.add(filesConverter.dtoFromFileEntity(file));
        }
        return dtoList;
    }
}
