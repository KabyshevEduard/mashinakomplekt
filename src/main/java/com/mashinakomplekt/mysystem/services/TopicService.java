package com.mashinakomplekt.mysystem.services;

import com.mashinakomplekt.mysystem.dao.TopicRepository;
import com.mashinakomplekt.mysystem.dto.TopicDto.TopicRequestDto;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.models.User;
import com.mashinakomplekt.mysystem.utils.JwtTokenUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class TopicService {
    private final TopicRepository topicRepository;
    private final JwtTokenUtil jwtTokenUtil;

    // Получить все свои topics
    public List<Topic> findAll(String token) throws InvalidParameterException {
        User user = jwtTokenUtil.checkUser(token);
        List<Topic> topics = topicRepository.findByUserId(user.getId());
        return topics;
    }

    // получить topic по id
    public Topic findById(String token, Long id) throws InvalidParameterException {
        User user = jwtTokenUtil.checkUser(token);
        Optional<Topic> topicOp = topicRepository.findById(id);
        if (!topicOp.isPresent()) {
            String errMasage = "Топика с таким id не существует";
            throw new InvalidParameterException(errMasage);
        }
        return topicOp.get();
    }

    // Добавить себе topic
    public Topic createTopic(String token, TopicRequestDto topicReq) throws InvalidParameterException {
        User user = jwtTokenUtil.checkUser(token);
        List<Topic> topics = topicRepository.findByUserId(user.getId());
        Optional<Topic> exsTopic = topics.stream().findAny();
        if (exsTopic.isPresent()) {
            throw new InvalidParameterException("Тема с таким названием уже существует");
        }
        Topic topic = new Topic();
        topic.setUser(user);
        topic.setTitle(topicReq.getTitle());
        topicRepository.save(topic);
        return topic;
    }

    // Удалить топик со всеми документами
    public Topic deleteTopic(String token, Long id) throws InvalidParameterException {
        User user = jwtTokenUtil.checkUser(token);
        Optional<Topic> topicOp = topicRepository.findById(id);
        if (!topicOp.isPresent()) {
            throw new InvalidParameterException("Такой темы не существует");
        }
        topicRepository.delete(topicOp.get());
        return topicOp.get();
    }
}
