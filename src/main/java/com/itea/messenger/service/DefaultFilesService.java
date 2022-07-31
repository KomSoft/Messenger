package com.itea.messenger.service;


import com.itea.messenger.converter.FilesConverter;
import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.entity.Files;
import com.itea.messenger.repository.FilesRepository;
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

    private void validateFileDto(FilesDto filesDto)throws ValidationException{
        if (isNull(filesDto)){
            throw new ValidationException("Object file is null");
            }
        if (isNull(filesDto.getFile()) || isNull(filesDto.getFileType())){
          throw new ValidationException("File or file type is null");
        }
    }
    @Override
    public FilesDto saveFile(FilesDto filesDto) throws ValidationException {
        validateFileDto(filesDto);
        Files files = filesRepository.save(filesConverter.fileEntityFromDto(filesDto));
        return filesConverter.dtoFromFileEntity(files);
    }

    @Override
    public FilesDto findById(Long id) throws ValidationException {
        Files files = filesRepository.findById(id).orElseThrow(() -> new ValidationException("No file is this id"));
        return filesConverter.dtoFromFileEntity(files);
    }

    @Override
    public List<FilesDto> findAll() {
        List<FilesDto> dtoList = new ArrayList<FilesDto>();
        List<Files> list = filesRepository.findAll();
        for (Files file: list
             ) {
            dtoList.add(filesConverter.dtoFromFileEntity(file));

        }
        return dtoList;
    }
}
