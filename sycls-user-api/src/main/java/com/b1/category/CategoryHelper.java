package com.b1.category;

import com.b1.category.dto.CategoryGetUserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Category Helper")
@Component
@RequiredArgsConstructor
public class CategoryHelper {

    private final CategoryQueryRepository categoryQueryRepository;

    public List<CategoryGetUserResponseDto> getAllCategoryOrderByNameAsc() {
        return categoryQueryRepository.getAllOrderByNameAscForUser();
    }
}