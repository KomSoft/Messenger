package com.itea.messenger.service;

import com.itea.messenger.converter.StatusLinksConverter;
import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.entity.StatusLinks;
import com.itea.messenger.repository.StatusLinksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultStatusLinksService implements StatusLinksService{
    private final StatusLinksRepository statusLinksRepository;
    private final StatusLinksConverter statusLinksConverter;
    
    private void validateStatusLinkDto(StatusLinksDto dto) throws ValidationException {
        if (isNull(dto)) {
            throw new ValidationException("Object statusLinks is null");
        }
        if (isNull(dto.getUserId()) || isNull(dto.getStatus())) {
            throw new ValidationException("User id or status is null");
        }
    }
    
    @Override
    public StatusLinksDto saveStatusLink(StatusLinksDto statusLinksDto) throws ValidationException {
        validateStatusLinkDto(statusLinksDto);
        StatusLinks savedStatusLinks = 
                statusLinksRepository.save(statusLinksConverter.statusLinksFromDto(statusLinksDto));
        return statusLinksDto;
    }

    @Override
    public StatusLinksDto findById(Long id) throws ValidationException {
        StatusLinks statusLinks = statusLinksRepository.findById(id).orElseThrow(() ->
                new ValidationException("No status links with this id"));
        return statusLinksConverter.dtoFromStatusLinks(statusLinks);
    }
}
