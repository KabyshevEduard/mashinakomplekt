package com.mashinakomplekt.mysystem.dto.DocumentDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentRequest {
    String title;
    String description;
}
