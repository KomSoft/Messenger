package com.itea.messenger.service;

import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.exception.ValidationException;
import java.util.List;

public interface StatusLinksService {
    StatusLinksDto saveStatusLink(StatusLinksDto statusLinksDto) throws ValidationException;

    StatusLinksDto findById(Long id) throws ValidationException;

    List<StatusLinksDto> findByMessageId(Long messageId) throws ValidationException;
}
