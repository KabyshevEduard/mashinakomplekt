package com.mashinakomplekt.mysystem.controllers;

import com.mashinakomplekt.mysystem.dto.DocumentDto.DocumentResponseDto;
import com.mashinakomplekt.mysystem.models.Document;
import com.mashinakomplekt.mysystem.services.DocumentService;
import com.mashinakomplekt.mysystem.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/my")
    public Map<String, List<DocumentResponseDto>> getMyDocuments(@RequestHeader(name = "Authorization") String token) {
        String tokenn = token.substring(7);
        Map<String, List<Document>> data = documentService.findMyAllDocuments(tokenn);
        log.info("Документы: " + data.toString());
        Map<String, List<DocumentResponseDto>> mapDocs = new HashMap<>();
        for (String key : data.keySet()) {
            List<DocumentResponseDto> docDto = data.get(key).stream().map(t -> new DocumentResponseDto(t)).collect(Collectors.toList());
            mapDocs.put(key, docDto);
        }
        return mapDocs;
    }

    @PostMapping("/add{topicId}")
    public ResponseEntity<DocumentResponseDto> addDocument(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam Long topicId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam("file") MultipartFile multipartFile
    ) {
        String tokenn = token.substring(7);

        // Метаданные файла
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        // Сделать глобальный обработчик IOException
        String filePath = null;
        try {
            filePath = FileUploadUtil.saveFile(fileName, multipartFile);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Document doc = documentService.createDocument(tokenn, topicId, title, description, filePath);
        log.info("Добавлен документ: title: " + title + ", description: " + description + ", path: " + filePath + ";");
        var docDto = new DocumentResponseDto(doc);
        return new ResponseEntity<DocumentResponseDto>(docDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete{documentId}")
    public ResponseEntity<DocumentResponseDto> deleteDocument(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam Long documentId
    ) {
        String tokenn = token.substring(7);
        Document doc = documentService.deleteDocument(tokenn, documentId);
        log.info("Удален документ: " + doc.toString());
        var docDto = new DocumentResponseDto(doc);
        return new ResponseEntity<DocumentResponseDto>(docDto, HttpStatus.OK);
    }
}
