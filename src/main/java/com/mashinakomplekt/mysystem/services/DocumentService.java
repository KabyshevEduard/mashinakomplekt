package com.mashinakomplekt.mysystem.services;

import com.mashinakomplekt.mysystem.dao.DocumentRepository;
import com.mashinakomplekt.mysystem.dto.DocumentDto.DocumentRequest;
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

    // Получить свои документы по title
    public Map<String, List<Document>> findDocumentsByTitle(String token,  String title) throws InvalidParameterException {
        User user = jwtTokenUtil.checkUser(token);
        List<Document> docs = documentRepository.findByUserIdAndTitleContainingIgnoreCase(user.getId(), title);
        HashMap<String, List<Document>> mapDocs = new HashMap<>();
        for (Document document : docs) {
            if (!mapDocs.containsKey(document.getTopic().getTitle())) {
                mapDocs.put(document.getTopic().getTitle(), new ArrayList<Document>(Arrays.asList(document)));
            } else {
                mapDocs.get(document.getTopic().getTitle()).add(document);
            }
        }
        return mapDocs;
    }

    // Добавление документов
    public Document createDocument(String token, Long topic_id, DocumentRequest documentReq) throws InvalidParameterException {
        Topic topic = topicService.findById(token, topic_id);
        Document doc = new Document();
        doc.setTitle(documentReq.getTitle());
        doc.setTopic(topic);

        // Сделать утилиту для создания пути
        doc.setPath("/data/" + documentReq.getTitle() + ".pdf");

        doc.setDescription(documentReq.getDescription());
        doc.setPublishedAt(LocalDateTime.now());
        documentRepository.save(doc);
        return doc;
    }
}
