package com.b1.content;

import com.b1.content.dto.ContentDetailImagePathGetAdminResponseDto;
import com.b1.content.dto.ContentGetAdminResponseDto;
import com.b1.content.dto.ContentSearchCondRequest;
import com.b1.content.entity.Content;
import com.b1.content.entity.ContentDetailImage;
import com.b1.exception.customexception.ContentNotChangeStatusException;
import com.b1.exception.customexception.ContentNotFoundException;
import com.b1.exception.errorcode.ContentErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Content Helper")
@Component
@RequiredArgsConstructor
public class ContentHelper {

    private final ContentRepository contentRepository;
    private final ContentQueryRepository queryRepository;

    /**
     * 카테고리가 있는지 확인
     */
    public boolean existsByCategoryId(final Long categoryId) {
        return contentRepository.existsByCategoryId(categoryId);
    }

    /**
     * 공연 저장
     */
    public void saveContent(final Content content) {
        contentRepository.save(content);
    }

    /**
     * 공연 엔티티 반환
     */
    public Content getContent(final Long contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(() -> {
                    log.error("공연을 찾지 못함 | contentId : {}", contentId);
                    return new ContentNotFoundException(ContentErrorCode.CONTENT_NOT_FOUND);
                });
    }

    /**
     * 서브이미지 리스트 반환
     */
    public List<ContentDetailImage> getByContentDetailImagesByContentId(final Long contentId) {
        return queryRepository.getByContentDetailImagesByContentId(contentId);
    }

    /**
     * 단일 조회시 필요한 공연의 정보 조회
     */
    public ContentGetAdminResponseDto getContentByContentId(final Long contentId) {
        return queryRepository.getByContentByContentIdForAdmin(contentId);
    }

    /**
     * 단일 조회시 필요한 공연의 서브이미지 정보 조회
     */
    public List<ContentDetailImagePathGetAdminResponseDto> getAllContentDetailImagesPathByContentId(
            final Long contentId) {
        return queryRepository.getAllContentDetailImagesPathByContentIdForAdmin(contentId);
    }

    /**
     * 단일 조회시 필요한 공연의 회차 정보 페이징
     */
    public Page<ContentGetAdminResponseDto> getAllContentForAdmin(
            final ContentSearchCondRequest request,
            final Pageable pageable) {
        return queryRepository.getAllContentForAdmin(request.getCategoryId(),
                request.getTitleKeyword(), request.getStatus(), pageable);
    }

    /**
     * 공연이 활성화 상태로 변환 가능인지 회차 확인
     */
    public void checkRoundStatusByContentId(final Long contentId) {
        Long count = queryRepository.checkRoundStatusByContentId(contentId);

        if (count == null || count == 0) {
            log.error("활성화된 회차 없음 | contentId : {}", contentId);
            throw new ContentNotChangeStatusException(ContentErrorCode.CONTENT_NOT_CHANGE_STATUS);
        }
    }
}
