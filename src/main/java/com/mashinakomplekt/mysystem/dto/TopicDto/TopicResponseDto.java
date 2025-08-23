package com.mashinakomplekt.mysystem.dto.TopicDto;

import com.mashinakomplekt.mysystem.models.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicResponseDto {
    private Long id;
    private String title;

    public TopicResponseDto(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
    }
}
