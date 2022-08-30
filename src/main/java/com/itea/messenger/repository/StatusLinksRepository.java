package com.itea.messenger.repository;

import com.itea.messenger.entity.StatusLinks;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.util.List;

public interface StatusLinksRepository extends JpaRepository<StatusLinks, Long> {

    List<StatusLinks> findByMessageId(Long messageId);

}
