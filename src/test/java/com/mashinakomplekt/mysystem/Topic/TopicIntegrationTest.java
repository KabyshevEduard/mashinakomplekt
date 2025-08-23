package com.mashinakomplekt.mysystem.Topic;

import com.mashinakomplekt.mysystem.dao.TopicRepository;
import com.mashinakomplekt.mysystem.dto.TopicDto.TopicRequest;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.services.TopicService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Transactional
@Rollback(false)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TopicIntegrationTest {

    private final TopicService topicService;
    private final EntityManager em;

    @Test
    public void createTopicTest() {
        TopicRequest topicRequest = new TopicRequest("Чертежи");

        // Нужен JWT токен
        Topic topic = topicService.createTopic("-",  topicRequest);

        TypedQuery<Topic> query = em.createQuery("select t from Topic t where t.title = :title", Topic.class);
        Topic topicTable = query.setParameter("title", "Чертежи").getSingleResult();

        Assertions.assertEquals(topicRequest.getTitle(), topicTable.getTitle());
    }
}
