package com.reacttest.reactspring.service;

import com.reacttest.reactspring.dto.PageRequestDTO;
import com.reacttest.reactspring.dto.PageResponseDTO;
import com.reacttest.reactspring.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductService {
    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

    //등록기능
    Long register (ProductDTO productDTO);

    //조회기능
    ProductDTO get(Long pno);

    //수정기능
    void modify(ProductDTO productDTO);

    //삭제 기능
    void remove(Long pno);


}
