package com.itea.messenger.service;

import com.itea.messenger.converter.StatusLinksConverter;
import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.entity.StatusLinks;
import com.itea.messenger.exception.ValidationException;
import com.itea.messenger.repository.StatusLinksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
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
    
/*
    @Override
    public StatusLinksDto saveStatusLink(StatusLinksDto statusLinksDto) throws ValidationException {
        validateStatusLinkDto(statusLinksDto);
        StatusLinks savedStatusLinks = 
                statusLinksRepository.save(statusLinksConverter.statusLinksFromDto(statusLinksDto));
        return statusLinksConverter.dtoFromStatusLinks(savedStatusLinks);
    }

*/
    @Override
    public StatusLinksDto findById(Long id) throws ValidationException {
        StatusLinks statusLinks = statusLinksRepository.findById(id).orElseThrow(() ->
                new ValidationException("No status links with this id"));
        return statusLinksConverter.statusLinksToDto(statusLinks);
    }

    @Override
    public List<StatusLinksDto> findByMessageId(Long messageId) {
        List<StatusLinks> statusLinks = statusLinksRepository.findByMessageId(messageId);
        return statusLinks.stream().map(statusLinksConverter::statusLinksToDto).collect(Collectors.toList());
    }

}
