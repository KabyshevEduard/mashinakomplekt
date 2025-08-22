package com.mashinakomplekt.mysystem.controllers;

import com.mashinakomplekt.mysystem.dto.TopicDto.TopicRequest;
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
    public ResponseEntity<Topic> addTopic(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody TopicRequest topicReq
    ) {
        String tokenn = token.substring(7);
        Topic topic = topicService.createTopic(tokenn, topicReq);
        log.info(topicReq.toString());
        return new ResponseEntity<Topic>(topic, HttpStatus.CREATED);
    }
}
