package com.mink.freshexpress.category.service.imp;

import com.mink.freshexpress.category.dto.*;
import com.mink.freshexpress.category.model.Category;
import com.mink.freshexpress.category.repository.CategoryRepository;
import com.mink.freshexpress.category.service.CategoryService;
import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.CategoryErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Transactional
    @Override
    public void create(CreateCategoryRequestDto dto) {
        //validate
        validateNewCategory(dto.getName());

        Category category = dto.toEntity();

        //상위 카테고리가 있을 경우
        String parentCategoryName = dto.getParentCategoryName();
        if (parentCategoryName == null || parentCategoryName.isBlank()) {//최상위 카테고리일 경우
            category.updateDepth(0L);
        } else {//상위 카테고리가 있는 경우
            List<Category> parentCategoryList = repository.findByNameContains(parentCategoryName);

            //상위 카테고리 validate
            if (parentCategoryList.size() > 1) {
                throw new CustomException(CategoryErrorCode.MULTIPLE_PARENT_CATEGORY);
            } else if (parentCategoryList.isEmpty() || !parentCategoryList.get(0).isEnable()) {
                throw new CustomException(CategoryErrorCode.PARENT_CATEGORY_NOT_FOUND);
            }


            //child 에 parent 할당
            Category parent = parentCategoryList.get(0);
            category.addParent(parent);

            //depth 추가 설정
            category.updateDepth(parent.getDepth() + 1);
        }
        repository.save(category);

    }

    private void validateNewCategory(String name) {
        List<Category> categoryList = repository.findByName(name);
        if (!categoryList.isEmpty()) {
            for (Category category : categoryList) {
                if (!category.isEnable()) {
                    throw new CustomException(CategoryErrorCode.ALREADY_DELETED);
                }
            }
            throw new CustomException(CategoryErrorCode.MULTIPLE_CATEGORY);
        }
    }

    @Override
    public CategoryResponseDto find(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND));

        CategoryResponseDto dto = new CategoryResponseDto(category.getName());

        //부모 카테고리 조회
        for (int depth = 0; depth < category.getDepth(); depth++) {
            Category parent = category.getParent();
            dto.addParent(new SimpleCategoryResponseDto(parent));
        }

        //자식 카테고리 조회
        category.getChildren().stream()
                .map(SimpleCategoryResponseDto::new)
                .forEach(dto::addChild);

        return dto;
    }

    @Override
    public List<SimpleCategoryResponseDto> search(SearchCategoryRequestDto dto) {
        List<Category> categoryList = repository.findByNameContains(dto.getName());
        //valid
        if (categoryList.isEmpty()) {
            throw new CustomException(CategoryErrorCode.NOT_FOUND);
        }
        String isEnable = dto.getIsEnable();

        return categoryList.stream()
                .filter(category -> {
                    if ("Y".equals(isEnable)) {
                        return !category.isEnable();
                    }
                    if ("N".equals(isEnable)) {
                        return category.isEnable();
                    }
                    return true;
                })
                .map(SimpleCategoryResponseDto::new)
                .toList();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND));
        category.delete();

    }

    @Transactional
    @Override
    public void update(Long id, UpdateCategoryRequestDto dto) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND));//valid
        if (dto.getNewParentId() != null) {
            Category parentCategory = repository.findById(Long.valueOf(dto.getNewParentId()))
                    .orElseThrow(() -> new CustomException(CategoryErrorCode.PARENT_CATEGORY_NOT_FOUND));//부모 카테고리 valid

            if (category.getDepth() == 0) {
                category.addParent(parentCategory);
            } else if (!category.getParent().equals(parentCategory)) {
                category.addParent(parentCategory);
            }

            //category depth 수정
            category.updateDepth(parentCategory.getDepth() + 1);
        }

        if (dto.getIsRestore() != null && !category.isEnable()) {
            category.restore();
        }
    }
}
