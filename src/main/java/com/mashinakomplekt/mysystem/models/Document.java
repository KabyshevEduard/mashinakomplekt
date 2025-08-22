package com.mashinakomplekt.mysystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
