package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Image {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String filePath;
        private Long fileSize;
        private String mediaType;
        @Type(type = "org.hibernate.type.BinaryType")
        @Lob
        private byte[] data;
        @ManyToOne
        private Ads ads;
    }

