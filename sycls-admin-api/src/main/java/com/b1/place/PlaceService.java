package com.b1.place;

import com.b1.common.PageResponseDto;
import com.b1.place.dto.PlaceAddRequestDto;
import com.b1.place.dto.PlaceGetResponseDto;
import com.b1.place.dto.PlaceSearchCondRequestDto;
import com.b1.place.dto.PlaceUpdateRequestDto;
import com.b1.place.entity.Place;
import com.b1.place.entity.PlaceStatus;
import com.b1.round.RoundHelper;
import com.b1.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Place Service")
@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final PlaceHelper placeHelper;
    private final RoundHelper roundHelper;

    /**
     * 공연장 등록
     */
    public void addPlace(final PlaceAddRequestDto requestDto) {
        Place place = Place.addPlace(
                requestDto.location(),
                requestDto.maxSeat(),
                requestDto.name()
        );

        placeHelper.savePlace(place);
    }

    /**
     * 공연장 전체 조회
     */
    @Transactional(readOnly = true)
    public PageResponseDto<PlaceGetResponseDto> getAllPlaces(
            final PlaceSearchCondRequestDto requestDto) {
        Sort.Direction direction = requestDto.getIsDesc() ? Direction.DESC : Direction.ASC;
        PageUtil.checkPageNumber(requestDto.getPageNum());
        Pageable pageable = PageRequest.of(requestDto.getPageNum() - 1, requestDto.getPageSize(),
                direction, requestDto.getOrderBy());

        Page<PlaceGetResponseDto> pageResponseDto = placeHelper.getAllPlaces(requestDto, pageable);
        return PageResponseDto.of(pageResponseDto);
    }

    /**
     * 공연장 단건 조회
     */
    @Transactional(readOnly = true)
    public PlaceGetResponseDto getPlace(final Long placeId) {
        Place place = placeHelper.getPlace(placeId);
        return PlaceGetResponseDto.of(place);
    }

    /**
     * 공연장 수정
     */
    public Long updatePlace(final Long placeId, final PlaceUpdateRequestDto requestDto) {
        Place place = placeHelper.getPlace(placeId);

        // 해당 공연장을 사용하고 회차상태가 AVAILABLE인 공연장 존재 여부 확인
        roundHelper.existsRoundByPlaceIdAndStatus(placeId);

        // maxSeat가 변경된 경우 공연장 disable 처리
        if (requestDto.maxSeat().equals(place.getMaxSeat())) {
            place.updatePlace(requestDto.location(), requestDto.name(),
                    requestDto.maxSeat(), PlaceStatus.DISABLE);
        }
        place.updatePlace(requestDto.location(), requestDto.name(),
                requestDto.maxSeat(), requestDto.status());
        return place.getId();
    }

    /**
     * 공연장 삭제
     */
    public void deletePlace(Long placeId) {
        Place place = placeHelper.getPlace(placeId);
        PlaceStatus.checkDeleted(place.getStatus());

        // 해당 공연장을 사용하고 회차상태가 AVAILABLE인 공연장 존재 여부 확인
        roundHelper.existsRoundByPlaceIdAndStatus(placeId);

        place.deletePlace();
    }
}
