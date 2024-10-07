package com.reacttest.reactspring.service;

import com.reacttest.reactspring.dto.PageRequestDTO;
import com.reacttest.reactspring.dto.PageResponseDTO;
import com.reacttest.reactspring.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testList(){
        //1page, 10 size
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<ProductDTO> result = productService.getList(pageRequestDTO);

        result.getDtoList().forEach(dto -> log.info(dto));
    }

    //등록 테스트
    @Test
    public void testRegister(){
        ProductDTO productDTO = ProductDTO.builder()
                .pname("새로운 상품")
                .pdesc("신규 추가 상품입니다.")
                .price(1000)
                .build();

        //UUID 필요
        productDTO.setUploadFileName(
                List.of(UUID.randomUUID() + "_" + "Test1.jpg",
                        UUID.randomUUID() + "_" + "Test2.jpg")
        );
        productService.register(productDTO);
    }

    //조회 테스트
    @Test
    public void testRead(){
        Long pno = 102L;

        ProductDTO productDTO = productService.get(pno);

        log.info(productDTO);
        log.info(productDTO.getUploadFileName());
    }

}
