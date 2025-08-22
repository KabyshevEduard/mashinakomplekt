package com.mashinakomplekt.mysystem.controllers;

import com.mashinakomplekt.mysystem.dto.DocumentDto.DocumentRequest;
import com.mashinakomplekt.mysystem.models.Document;
import com.mashinakomplekt.mysystem.models.Topic;
import com.mashinakomplekt.mysystem.services.DocumentService;
import com.mashinakomplekt.mysystem.services.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/my")
    public Map<String, List<Document>> getMyDocuments(@RequestHeader(name = "Authorization") String token) {
        String tokenn = token.substring(7);
        Map<String, List<Document>> data = documentService.findMyAllDocuments(tokenn);
        log.info(data.toString());
        return data;
    }

    @GetMapping("/my{title}")
    public Map<String, List<Document>> getDocumentsByTitle(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam String title
    ) {
        String tokenn = token.substring(7);
        Map<String, List<Document>> data = documentService.findDocumentsByTitle(tokenn, title);
        log.info(data.toString());
        return data;
    }

    @PostMapping("/add{topic_id}")
    public ResponseEntity<Document> addDocument(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam Long topic_id,
            @RequestBody DocumentRequest documentReq
    ) {
        Document doc = documentService.createDocument(token, topic_id, documentReq);
        log.info(documentReq.toString());
        return new ResponseEntity<Document>(doc, HttpStatus.CREATED);
    }
}
