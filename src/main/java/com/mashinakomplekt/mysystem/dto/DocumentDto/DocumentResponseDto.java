package com.mashinakomplekt.mysystem.dto.DocumentDto;

import com.mashinakomplekt.mysystem.models.Document;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DocumentResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime publishedAt;
    private String path;

    public DocumentResponseDto(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.description = document.getDescription();
        this.publishedAt = document.getPublishedAt();
        this.path = document.getPath();
    }
}
