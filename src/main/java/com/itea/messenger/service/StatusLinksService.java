package com.itea.messenger.service;

import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.exception.ValidationException;
import java.util.List;

public interface StatusLinksService {
    StatusLinksDto findById(Long id) throws NotFoundException;
    List<StatusLinksDto> findByMessageId(Long messageId) throws NotFoundException;
}
