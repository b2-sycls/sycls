package com.b1.category;

import com.b1.category.dto.CategoryGetResponseDto;
import com.b1.category.entity.Category;
import com.b1.exception.customexception.CateGoryNotFoundException;
import com.b1.exception.errorcode.CategoryErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Category Adapter")
@Component
@RequiredArgsConstructor
public class CategoryAdapter {

    private final CategoryRepository categoryRepository;
    private final CategoryCustomRepository customRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("카테고리를 찾지 못함 | request : {}", categoryId);
                    return new CateGoryNotFoundException(
                            CategoryErrorCode.CATEGORY_NOT_FOUND);
                });
    }

    public List<CategoryGetResponseDto> findAllOrderByNameAsc() {
        return customRepository.findAllOrderByNameAsc();
    }
}
