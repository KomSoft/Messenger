package com.itea.messenger.converter;

import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.entity.StatusLinks;
import org.springframework.stereotype.Component;

@Component
public class StatusLinksConverter {
    public StatusLinks statusLinksFromDto(StatusLinksDto statusLinksDto) {
        StatusLinks statusLinks = new StatusLinks();
        statusLinks.setId(statusLinksDto.getId());
        statusLinks.setStatus(statusLinksDto.getStatus());
        statusLinks.setUserId(statusLinksDto.getUserId());
        return statusLinks;
    }

    public StatusLinksDto dtoFromStatusLinks(StatusLinks statusLinks) {
        StatusLinksDto dto = new StatusLinksDto();
        dto.setId(statusLinks.getId());
        dto.setStatus(statusLinks.getStatus());
        dto.setUserId(statusLinks.getUserId());
        return dto;
    }
}
