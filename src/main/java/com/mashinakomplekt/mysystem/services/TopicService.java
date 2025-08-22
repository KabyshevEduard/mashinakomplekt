package com.mashinakomplekt.mysystem.services;

import com.mashinakomplekt.mysystem.dao.TopicRepository;
import com.mashinakomplekt.mysystem.dto.TopicDto.TopicRequest;
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
            log.error(errMasage);
            throw new InvalidParameterException(errMasage);
        }
        return topicOp.get();
    }

    // Добавить себе topic
    public Topic createTopic(String token, TopicRequest topicReq) throws InvalidParameterException {
        // Добавить логику проверки, если уже существует топик с таким названием

        User user = jwtTokenUtil.checkUser(token);
        Topic topic = new Topic();
        topic.setUser(user);
        topic.setTitle(topicReq.getTitle());
        topicRepository.save(topic);
        return topic;
    }
}
