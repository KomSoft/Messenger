package com.itea.messenger.service;

import com.itea.messenger.dto.StatusLinksDto;

import javax.persistence.Id;
import java.util.List;

public interface StatusLinksService {
    StatusLinksDto saveStatusLink(StatusLinksDto statusLinksDto) throws ValidationException;

    StatusLinksDto findById(Long id) throws ValidationException;
}
