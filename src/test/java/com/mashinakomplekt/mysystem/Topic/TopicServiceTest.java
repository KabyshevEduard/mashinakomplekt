package com.mashinakomplekt.mysystem.Topic;

import com.mashinakomplekt.mysystem.dao.RoleRepository;
import com.mashinakomplekt.mysystem.dto.JwtDto.JwtRequest;
import com.mashinakomplekt.mysystem.dto.TopicDto.TopicRequestDto;
import com.mashinakomplekt.mysystem.dto.UserDto.RegistartionUserRequest;
import com.mashinakomplekt.mysystem.models.Role;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.services.AuthService;
import com.mashinakomplekt.mysystem.services.TopicService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidParameterException;


@Transactional
@SpringBootTest(
        properties = {"spring.datasource.url=jdbc:postgresql://localhost:5432/TestDatabase", "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class TopicServiceTest {

    private final TopicService topicService;
    private final EntityManager em;
    private final RoleRepository roleRepository;
    private final AuthService authService;

    @Autowired
    public TopicServiceTest(TopicService topicService, EntityManager em, RoleRepository roleRepository, AuthService authService) {
        this.topicService = topicService;
        this.em = em;
        this.roleRepository = roleRepository;
        this.authService = authService;
    }

    @Test
    public void createTopicTest() throws InvalidParameterException {
        putRole();
        String token = getToken();

        TopicRequestDto topicRequest = new TopicRequestDto("Документы");

        topicService.createTopic(token, topicRequest);

        TypedQuery<Topic> query = em.createQuery("select t from Topic t where t.title = :title", Topic.class);
        Topic topic = query.setParameter("title", topicRequest.getTitle()).getSingleResult();

        Assertions.assertEquals(topicRequest.getTitle(), topic.getTitle());
    }

    @Test
    public void deleteTopicTest() throws InvalidParameterException {
        putRole();
        String token = getToken();

        // Тест для удаления
    }

    private void putRole() {
        Role role = new Role();
        role.setKind("ROLE_USER");
        roleRepository.save(role);
    }

    private String getToken() {
        RegistartionUserRequest registartionUserRequest = new RegistartionUserRequest();
        registartionUserRequest.setName("Eduard");
        registartionUserRequest.setUsername("eduard2002");
        registartionUserRequest.setEmail("eduardkabyshev@vk.com");
        registartionUserRequest.setPassword("lex2mubds");
        registartionUserRequest.setConfirmPassword("lex2mubds");

        authService.createNewUser(registartionUserRequest);

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(registartionUserRequest.getUsername());
        jwtRequest.setPassword(registartionUserRequest.getPassword());

        String token = authService.createAuthToken(jwtRequest).get();
        return token;
    }
}
