package com.itea.messenger.entity;

import com.itea.messenger.dto.FilesDto;
import com.itea.messenger.type.FileTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
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

    public Files(String fileName) {
        this.fileName = fileName;
        this.fileType = FilesDto.determineFileType(fileName);
    }

}
