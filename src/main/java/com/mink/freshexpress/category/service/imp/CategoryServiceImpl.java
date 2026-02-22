package com.mink.freshexpress.category.service.imp;

import com.mink.freshexpress.category.dto.CreateCategoryRequestDto;
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
        Category category = dto.toEntity();
        //상위 카테고리가 있을 경우
        String parentCategoryName = dto.getParentCategoryName();
        if (!parentCategoryName.isBlank()) {
            List<Category> parentCategoryList = repository.findByNameLikeIgnoreCase(parentCategoryName);

            //상위 카테고리 validate
            if (parentCategoryList.size() > 1) {
                throw new CustomException(CategoryErrorCode.MULTIPLE_PARENT_CATEGORY);
            } else if (parentCategoryList.isEmpty()) {
                throw new CustomException(CategoryErrorCode.PARENT_CATEGORY_NOT_FOUND);
            }

            //parent에 child 추가
            Category parent = parentCategoryList.get(0);
            parent.addChildren(category);

            //depth 추가 설정
            category.updateDepth(parent.getDepth() + 1);
        } else {//최상위 카테고리일 경우
            category.updateDepth(0L);
        }


    }
}
