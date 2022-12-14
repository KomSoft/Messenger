package com.itea.messenger.service;

import com.itea.messenger.converter.StatusLinksConverter;
import com.itea.messenger.dto.StatusLinksDto;
import com.itea.messenger.entity.StatusLinks;
import com.itea.messenger.exception.NotFoundException;
import com.itea.messenger.repository.StatusLinksRepository;
import com.itea.messenger.type.MessageStatus;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultStatusLinksService implements StatusLinksService{

    @Autowired
    private StatusLinksRepository statusLinksRepository;
    @Autowired
    private StatusLinksConverter statusLinksConverter;
    
    @Override
    public StatusLinksDto findById(Long id) throws NotFoundException {
        StatusLinks statusLinks = statusLinksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Status Links with id:" + id));
        return statusLinksConverter.statusLinksToDto(statusLinks);
    }

    @Override
    public List<StatusLinksDto> findByMessageId(Long messageId) throws NotFoundException {
        List<StatusLinks> statusLinks = statusLinksRepository.findByMessageId(messageId);
        if (statusLinks.size() == 0) {
            throw new NotFoundException("No Statuses for message id:" + messageId);
        }
        return statusLinks.stream().map(statusLinksConverter::statusLinksToDto).collect(Collectors.toList());
    }

    public void saveStatusById(Long id, MessageStatus messageStatus) throws NotFoundException {
        StatusLinks status = statusLinksRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(MessageFormat.format("Status for id:{0} not found", id)));
        status.setStatus(messageStatus);
        statusLinksRepository.save(status);
    }

}
