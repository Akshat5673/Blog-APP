package com.blogApp.blog.Utils;

import com.blogApp.blog.Adapters.GenericEntityDtoAdapter;
import com.blogApp.blog.Payloads.PageResponse;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageResponseUtil {

    public Pageable createPageable(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber, pageSize, sort);
    }

    public <T, U> PageResponse<U> createPageResponse(Page<T> page, Class<U> dtoClass) {

        PageResponse<U> pageResponse = new PageResponse<>();
        pageResponse.setContent(GenericEntityDtoAdapter.toDtoList(page.getContent(), dtoClass));
        pageResponse.setPageNumber(page.getNumber());
        pageResponse.setPageSize(page.getSize());
        pageResponse.setTotalElements(page.getTotalElements());
        pageResponse.setTotalPages(page.getTotalPages());
        pageResponse.setLastPage(page.isLast());

        return pageResponse;
    }
}
