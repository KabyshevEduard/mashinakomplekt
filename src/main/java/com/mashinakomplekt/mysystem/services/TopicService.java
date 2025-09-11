package com.mashinakomplekt.mysystem.services;

import com.mashinakomplekt.mysystem.dao.TopicRepository;
import com.mashinakomplekt.mysystem.dto.TopicDto.TopicRequestDto;
import com.mashinakomplekt.mysystem.models.Document;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.models.User;
import com.mashinakomplekt.mysystem.utils.JwtTokenUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Data
public class TopicService {
    private final TopicRepository topicRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    // Получить все свои topics
    public List<Topic> findAll(String token) {
        User user = jwtTokenUtil.checkUser(token);
        List<Topic> topics = topicRepository.findAllByUserId(user.getId());
        return topics;
    }

    // Получить topic у user по id
    public Topic findUserTopicById(String token, Long id) throws InvalidParameterException {
        User user = jwtTokenUtil.checkUser(token);
        Topic topic = user.getTopics().stream().findFirst().orElseThrow(InvalidParameterException::new);
        return topic;
    }

    // Получить topic по id
    public Topic findById(Long id) throws InvalidParameterException {
        Topic topic = topicRepository.findById(id).orElseThrow(InvalidParameterException::new);
        return topic;
    }

    // Добавить себе topic
    public Topic createTopic(String token, TopicRequestDto topicReq) throws UsernameNotFoundException, InvalidParameterException {
        User user = jwtTokenUtil.checkUser(token);
        List<Topic> topics = user.getTopics();
        String newTitle = topicReq.getTitle();

        Topic newTopic = new Topic();
        newTopic.setTitle(topicReq.getTitle());
        newTopic.setUsers(List.of(user));

        if (topics != null) {
            Optional<Topic> topic = topics.stream().filter(t -> t.getTitle().equals(newTitle)).findFirst();
            if (topic.isPresent()) {
                throw new InvalidParameterException("Такая тема уже существует");
            }
            List<Topic> newTopics = new ArrayList<>(topics);
            newTopics.add(newTopic);
            user.setTopics(newTopics);
            Topic savedTopic = topicRepository.save(newTopic);
            return savedTopic;
        }
        user.setTopics(List.of(newTopic));
        Topic topic = topicRepository.save(newTopic);
        return topic;
    }

    // Удалить топик со всеми документами
    public Topic deleteTopic(String token, Long id) throws InvalidParameterException {
        User user = jwtTokenUtil.checkUser(token);
        Optional<Topic> topicOp = topicRepository.findById(id);
        if (!topicOp.isPresent()) {
            throw new InvalidParameterException("Такой темы не существует");
        }
        Topic topic = topicOp.get();
        topicRepository.delete(topic);
        return topic;
    }
}
