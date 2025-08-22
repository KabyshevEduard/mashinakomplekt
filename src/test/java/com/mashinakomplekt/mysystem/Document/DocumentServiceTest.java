package com.mashinakomplekt.mysystem.Document;


import com.mashinakomplekt.mysystem.dao.DocumentRepository;
import com.mashinakomplekt.mysystem.models.Document;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.models.User;
import com.mashinakomplekt.mysystem.services.DocumentService;
import com.mashinakomplekt.mysystem.services.TopicService;
import com.mashinakomplekt.mysystem.utils.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository mockDocumentRepository;

    @Mock
    private TopicService mockTopicService;

    @Mock
    private JwtTokenUtil mockJwtTokenUtil;

    @Test
    public void testFindMyAllDocuments() {
        DocumentService documentService = new DocumentService(mockDocumentRepository, mockTopicService, mockJwtTokenUtil);

        User user = new User();
        user.setId(1L);
        user.setUsername("e2rd");
        user.setEmail("eduardkabyshev@vk.com");
        user.setPassword("oriuwpoqueroqerqpoerjdfnopa");
        user.setName("eduard");

        Document doc = new Document();
        doc.setId(1L);
        doc.setTitle("Чертеж глины");
        doc.setDescription("Я ем чизборга");
        doc.setPublishedAt(LocalDateTime.now());
        doc.setPath("c:\\data\\zhopa.pdf");

        List<Document> documents = new ArrayList<>();
        documents.add(doc);

        Topic topic = new Topic();
        topic.setId(1L);
        topic.setTitle("Раздел новый");
        topic.setUser(user);
        topic.setDocuments(documents);

        List<Topic> topics = new ArrayList<>();
        topics.add(topic);

        Map<String, List<Document>> testMap = new HashMap<String, List<Document>>();
        testMap.put("Раздел новый", documents);

        Mockito
                .when(mockTopicService.findAll(Mockito.anyString()))
                .thenReturn(topics);

        Assertions.assertEquals(testMap, documentService.findMyAllDocuments("-"));
    }

    @Test
    public void testFindDocumentsByTitleWithMockito() {
        DocumentService documentService = new DocumentService(mockDocumentRepository, mockTopicService, mockJwtTokenUtil);

        User user = new User();
        user.setId(1L);
        user.setUsername("e2rd");
        user.setEmail("eduardkabyshev@vk.com");
        user.setPassword("oriuwpoqueroqerqpoerjdfnopa");
        user.setName("eduard");

        Mockito
                .when(mockJwtTokenUtil.checkUser(Mockito.anyString()))
                .thenReturn(user);

        Topic topic = new Topic();
        topic.setId(1L);
        topic.setTitle("Раздел новый");
        topic.setUser(user);


        Document doc = new Document();
        doc.setId(1L);
        doc.setTitle("Чертеж глины");
        doc.setDescription("Я ем чизборга");
        doc.setPublishedAt(LocalDateTime.now());
        doc.setPath("c:\\data\\zhopa.pdf");
        doc.setTopic(topic);

        List<Document> documents = new ArrayList<>();
        documents.add(doc);

        Mockito
                .when(mockDocumentRepository.findByUserIdAndTitleContainingIgnoreCase(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(documents);

        Map<String, List<Document>> testMap = new HashMap<String, List<Document>>();
        testMap.put("Раздел новый", documents);

        Assertions.assertEquals(testMap,  documentService.findDocumentsByTitle("-", "глины"));
        Assertions.assertEquals(testMap,  documentService.findDocumentsByTitle("-", "ГлИнЫ"));
    }
}
