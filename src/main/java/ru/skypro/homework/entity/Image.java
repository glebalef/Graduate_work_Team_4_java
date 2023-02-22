package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
    @Entity
    @Table(name = "image")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Image {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;
        @Column(name = "file_path")
        private String filePath;
        @Column(name = "file_size")

        private long fileSize;
        @Column(name = "media_type")
        private String mediaType;
        @Lob
        @Type(type = "org.hibernate.type.BinaryType")
        @Column(name = "data")
        private byte[] data;
        @ManyToOne
        private Ads ads;
    }

