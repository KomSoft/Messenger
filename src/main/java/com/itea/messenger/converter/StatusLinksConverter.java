package com.itea.messenger.converter;

import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.entity.StatusLinks;
import org.springframework.stereotype.Component;

@Component
public class StatusLinksConverter {

    public StatusLinksDto statusLinksToDto(StatusLinks statusLinks) {
        StatusLinksDto statusLinksDto = new StatusLinksDto();
        statusLinksDto.setId(statusLinks.getId());
        statusLinksDto.setMessageId(statusLinks.getMessageId());
        statusLinksDto.setUserId(statusLinks.getUserId());
        statusLinksDto.setStatus(statusLinks.getStatus());
        return statusLinksDto;
    }
}
