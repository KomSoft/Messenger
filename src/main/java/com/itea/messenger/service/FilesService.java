package com.itea.messenger.service;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.exception.ValidationException;

import java.util.List;

public interface FilesService {
    FilesDto saveFile(FilesDto filesDto);
    FilesDto findById(Long id);
    List<FilesDto> findAll();
}
