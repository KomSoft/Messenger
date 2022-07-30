package com.itea.messenger.service;

import com.itea.messenger.dto.StatusLinksDto;

import javax.persistence.Id;
import java.util.List;

public interface StatusLinksService {
    StatusLinksDto saveStatusLink(StatusLinksDto statusLinksDto) throws ValidationException;

    List<StatusLinksDto> findByUserId(Long userId);

    List<StatusLinksDto> findAll();

    StatusLinksDto findById(Long id) throws ValidationException;
}
