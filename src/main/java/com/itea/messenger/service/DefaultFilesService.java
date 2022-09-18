package com.itea.messenger.service;

import com.itea.messenger.converter.FilesConverter;
import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.entity.Files;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.FilesRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultFilesService implements FilesService {

    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private FilesConverter filesConverter;

    @Override
    public FilesDto saveFile(FilesDto filesDto) throws ValidationException {
        Files file = filesRepository.save(filesConverter.fileEntityFromDto(filesDto));
        return filesConverter.fileEntityToDto(file);
    }

    @Override
    public FilesDto findById(Long id) throws NotFoundException {
        Files file = filesRepository.findById(id).orElseThrow(() -> new NotFoundException("File with id:" + id + " not found"));
        return filesConverter.fileEntityToDto(file);
    }

    @Override
    public List<FilesDto> findAll() throws NotFoundException {
        List<Files> files = filesRepository.findAll();
        if (files.size() == 0) {
            throw new NotFoundException("Files records not found");
        }
        return files.stream().map(filesConverter::fileEntityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) throws EmptyResultDataAccessException {
        filesRepository.deleteById(id);
    }

//    User & Message entities have "orphanRemoval = true" then we don't need deleteById(id) method
}
