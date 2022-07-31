package com.itea.messenger.entity;

import com.itea.messenger.enums.fileType.FileTypes;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "file_table")

public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String file;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileTypes fileType;
}
