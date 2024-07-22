package com.b1.place;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.place.dto.PlaceAddRequestDto;
import com.b1.place.dto.PlaceGetResponseDto;
import com.b1.place.dto.PlaceSearchCondiRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "PlaceRestController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PlaceRestController {

    private final PlaceService placeService;

    /**
     * 공연장 등록
     */
    @PostMapping("/places")
    public ResponseEntity<RestApiResponseDto> addPlace(
            @Valid @RequestBody final PlaceAddRequestDto requestDto
    ) {
        placeService.addPlace(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(HttpStatus.OK.value(), "성공"));
    }

    /**
     * 공연장 전체 조회
     */
    @GetMapping("/places")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<PlaceGetResponseDto>>> getAllPlace(
            @ModelAttribute final PlaceSearchCondiRequestDto requestDto
    ) {
        PageResponseDto<PlaceGetResponseDto> responseDto = placeService.getAllPlaces(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(HttpStatus.OK.value(), "성공", responseDto));
    }

    /**
     * 공연장 단건 조회
     */
    @GetMapping("/{placeId}")
    public ResponseEntity<RestApiResponseDto<PlaceGetResponseDto>> getPlace(
            @PathVariable Long placeId
    ) {
        PlaceGetResponseDto responseDto = placeService.getPlace(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(HttpStatus.OK.value(), "성공", responseDto));
    }

}
