package com.mashinakomplekt.mysystem.controllers;

import com.mashinakomplekt.mysystem.dto.TopicDto.TopicRequestDto;
import com.mashinakomplekt.mysystem.dto.TopicDto.TopicResponseDto;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.services.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
@Slf4j
public class TopicController {

    private final TopicService topicService;

    @PostMapping("/add")
    public ResponseEntity<TopicResponseDto> addTopic(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody TopicRequestDto topicReq
    ) {
        String tokenn = token.substring(7);
        Topic topic = topicService.createTopic(tokenn, topicReq);
        log.info("Добавлен " + topicReq.toString());
        var topicDto = new TopicResponseDto(topic);
        return new ResponseEntity<TopicResponseDto>(topicDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete{deleteId}")
    public ResponseEntity<TopicResponseDto> deleteTopic(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam Long topicId
    ) {
        String tokenn = token.substring(7);
        Topic topic = topicService.deleteTopic(tokenn, topicId);
        log.info("Удален" + topic.toString());
        var topicDto = new TopicResponseDto(topic);
        return new ResponseEntity<TopicResponseDto>(topicDto, HttpStatus.OK);
    }
}
