package com.mashinakomplekt.mysystem.Document;

import com.mashinakomplekt.mysystem.dto.DocumentDto.DocumentRequest;
import com.mashinakomplekt.mysystem.services.DocumentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
/*
@Transactional
@Rollback(false)
@RequiredArgsConstructor(onConstructor = @Autowired)
@SpringJUnitConfig({DocumentService.class})
public class DocumentIntegrationTest {

    @Test
    public void createDocument() {
        DocumentRequest documentReq = new DocumentRequest("Чертеж глины", "Этот чертеж глины стоит миллиарды долларов, как ни крути");

    }
}
*/