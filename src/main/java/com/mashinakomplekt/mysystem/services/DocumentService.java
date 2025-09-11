package com.mashinakomplekt.mysystem.services;

import com.mashinakomplekt.mysystem.dao.DocumentRepository;
import com.mashinakomplekt.mysystem.models.Document;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.models.User;
import com.mashinakomplekt.mysystem.utils.JwtTokenUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final TopicService topicService;
    private final JwtTokenUtil jwtTokenUtil;


    // Получить все свои документы
    public Map<String, List<Document>> findMyAllDocuments(String token) throws InvalidParameterException {
        List<Topic> topics = topicService.findAll(token);
        Map<String, List<Document>> mapDocs = topics.stream().collect(Collectors.toMap(t -> t.getTitle(), t -> t.getDocuments()));
        return mapDocs;
    }

    // Добавление документа
    public Document createDocument(String token, Long topicId, String title, String description, String filePath) throws InvalidParameterException {
        topicService.findUserTopicById(token, topicId);
        Topic topic = topicService.findById(topicId);
        Document doc = new Document();
        doc.setTitle(title);
        doc.setTopic(topic);
        doc.setPath(filePath);
        doc.setDescription(description);
        doc.setPublishedAt(LocalDateTime.now());
        documentRepository.save(doc);
        return doc;
    }

    // Удаление документа
    public Document deleteDocument(String token, Long documentId) throws InvalidParameterException {
        User user = jwtTokenUtil.checkUser(token);
        Optional<Document> doc = documentRepository.findById(documentId);
        if (!doc.isPresent()) {
            throw new InvalidParameterException("Документ " + documentId + " не найден");
        }
        documentRepository.delete(doc.get());
        return doc.get();
    }
}
