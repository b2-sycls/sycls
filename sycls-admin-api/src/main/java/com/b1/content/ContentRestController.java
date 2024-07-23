package com.b1.content;

import com.b1.content.dto.ContentAddRequestDto;
import com.b1.globalresponse.RestApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "Content Rest Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ContentRestController {

    private final ContentService contentService;

    @PostMapping("/contents")
    public ResponseEntity<RestApiResponseDto<String>> addContent(
            @Valid @RequestPart("dto") ContentAddRequestDto contentAddRequestDto,
            @RequestPart(value = "mainImage") MultipartFile mainImage,
            @RequestPart(value = "detailImages", required = false) MultipartFile[] detailImages) {
        contentService.addContent(contentAddRequestDto, mainImage, detailImages);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("등록 성공"));
    }
}
