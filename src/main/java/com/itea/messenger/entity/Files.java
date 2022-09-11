package com.itea.messenger.entity;

import com.itea.messenger.type.FileTypes;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "files")

public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileTypes fileType;

    @OneToOne(mappedBy = "file", orphanRemoval = true)
    private Messages message;

    @OneToOne(mappedBy = "avatar", orphanRemoval = true)
    private Users user;
}
