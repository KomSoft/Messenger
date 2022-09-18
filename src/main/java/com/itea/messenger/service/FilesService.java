package com.itea.messenger.service;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface FilesService {
    FilesDto saveFile(FilesDto filesDto) throws ValidationException;
    FilesDto findById(Long id) throws ValidationException, NotFoundException;
    List<FilesDto> findAll() throws NotFoundException;
    void deleteById(Long id) throws EmptyResultDataAccessException;
}
