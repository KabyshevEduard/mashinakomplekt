package com.mashinakomplekt.mysystem.Document.integration;

import com.mashinakomplekt.mysystem.dao.RoleRepository;
import com.mashinakomplekt.mysystem.dao.UserRepository;
import com.mashinakomplekt.mysystem.dto.JwtDto.JwtRequest;
import com.mashinakomplekt.mysystem.dto.TopicDto.TopicRequestDto;
import com.mashinakomplekt.mysystem.dto.UserDto.RegistartionUserRequest;
import com.mashinakomplekt.mysystem.models.Document;
import com.mashinakomplekt.mysystem.models.Role;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.models.User;
import com.mashinakomplekt.mysystem.services.AuthService;
import com.mashinakomplekt.mysystem.services.DocumentService;
import com.mashinakomplekt.mysystem.services.TopicService;
import com.mashinakomplekt.mysystem.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest(
        properties = {"spring.datasource.url=jdbc:postgresql://localhost:5432/TestDatabase", "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class DocumentServiceTest {

    private final DocumentService documentService;
    private final EntityManager em;
    private final RoleRepository roleRepository;
    private final TopicService topicService;
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public DocumentServiceTest(DocumentService documentService,
                               EntityManager em, RoleRepository roleRepository,
                               AuthService authService, TopicService topicService,
                               UserService userService) {
        this.documentService = documentService;
        this.em = em;
        this.roleRepository = roleRepository;
        this.authService = authService;
        this.topicService = topicService;
        this.userService = userService;
    }

    @Test
    public void createDocumentTest() {
        putRole();
        String token = getToken();
        TopicRequestDto topicRequestDto = new TopicRequestDto("Глины");
        Topic topic = topicService.createTopic(token, topicRequestDto);
        documentService.createDocument(
                token,
                topic.getId(),
                "New document",
                "what the hell are you talking about",
                "C://Users/Desktop"
        );

        TypedQuery<Document> query = em.createQuery("select d from Document d where d.title = :title", Document.class);
        Document doc = query.setParameter("title", "New document").getSingleResult();
        Assertions.assertEquals("New document", doc.getTitle());
    }

    // Тесты для удаления и получения всех записей

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
