package com.mashinakomplekt.mysystem.Topic.unit;

import com.mashinakomplekt.mysystem.dao.TopicRepository;
import com.mashinakomplekt.mysystem.dto.TopicDto.TopicRequestDto;
import com.mashinakomplekt.mysystem.models.Role;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.models.User;
import com.mashinakomplekt.mysystem.services.TopicService;
import com.mashinakomplekt.mysystem.services.UserService;
import com.mashinakomplekt.mysystem.utils.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


public class TopicServiceUnitTest {

    @Test
    public void createTopicTest() {
        UserService mockUserService = Mockito.mock(UserService.class);
        JwtTokenUtil mockJwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
        TopicRepository mockTopicRepository = Mockito.mock(TopicRepository.class);
        TopicService topicService = new TopicService(
                mockTopicRepository,
                mockJwtTokenUtil,
                mockUserService
        );
        Role role = new Role();
        role.setKind("ROLE_USER");
        Topic topic = new Topic();
        topic.setTitle("testtopic");
        User user = new User();
        user.setName("Testname");
        user.setPassword("12345678");
        user.setEmail("rockstar.eeeee@vk.com");
        user.setRoles(List.of(role));
        user.setUsername("testuser");
        user.setTopics(new ArrayList<Topic>(List.of(topic)));

        Mockito
                .when(mockJwtTokenUtil.checkUser(Mockito.anyString()))
                .thenReturn(user);

        // Пустой тест 🙂
    }
}
