package com.itea.messenger.service;

import com.itea.messenger.converter.FilesConverter;
import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.entity.Files;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.FilesRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultFilesService implements FilesService {
    private final FilesRepository filesRepository;
    private final FilesConverter filesConverter;

    @SneakyThrows
    @Override
    public FilesDto saveFile(FilesDto filesDto) {
//        validateFileDto(filesDto);
        Files file = filesRepository.save(filesConverter.fileEntityFromDto(filesDto));
        return filesConverter.fileEntityToDto(file);
    }

    @SneakyThrows
    @Override
    public FilesDto findById(Long id) {
        Files file = filesRepository.findById(id).orElseThrow(() -> new ValidationException("No file with id:" + id));
        return filesConverter.fileEntityToDto(file);
    }

    @Override
    public List<FilesDto> findAll() {
        return filesRepository.findAll().stream().map(filesConverter::fileEntityToDto).collect(Collectors.toList());
    }
}
