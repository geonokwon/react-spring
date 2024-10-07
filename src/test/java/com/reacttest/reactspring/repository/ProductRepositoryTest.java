package com.reacttest.reactspring.repository;

import com.reacttest.reactspring.domain.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testInsert(){
        for(int i = 0; i < 100; i++){
            Product product = Product.builder()
                    .pname("상품" + i)
                    .price(100 * i)
                    .pdesc("상품설명 " + i)
                    .build();

            //2개의 이미지 파일 추가
            product.addImageString(UUID.randomUUID().toString() + "_" + "IMAGE1.jpg");
            product.addImageString(UUID.randomUUID().toString() + "_" + "IMAGE2.jpg");
            productRepository.save(product);
        }
    }

    @Transactional
    @Test
    public void testRead(){
        //@ElementCollection 은 기본적으로 Lazy loading 방식으로 작동함
        //Lazy loading = 필요한 데이블만 먼저 조회한다
        Long pno = 1L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product); //----------------1
        log.info(product.getImageList()); //----------------2

    }

    @Test
    public void testRead2() {

        //EntityGraph 를 이용해서 쿼리 실행횟수를 줄일수 있다 해당속성을 조인처리 하도록 설정
        //Transactional 을 사용하지 않아도 한번에 처리하는것을 test 해볼수 있음!
        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        log.info(product);
        log.info(product.getImageList());
    }

    //삭제(수정) 테스트
    @Commit
    @Transactional
    @Test
    public void testDelete(){
       Long pno = 2L;
       productRepository.updateToDelete(pno, true);
    }


    @Test
    public void testUpdate() {
        Long pno = 11L;

        Optional<Product> product = productRepository.selectOne(pno);

        product.get().changeName("11번 상품");
        product.get().changeDesc("11번 상품입니다.");
        product.get().changePrice(5000);
//        product.changeName("10번 상품");
//        product.changeDesc("10번 상품입니다.");
//        product.changePrice(5000);

        //첨부파일수정(기존이미지 삭제(clearList())
        product.get().clearList();

        product.get().addImageString(UUID.randomUUID().toString() + "_" + "NEWIMAGE1.jpg");
        product.get().addImageString(UUID.randomUUID().toString() + "_" + "NEWIMAGE2.jpg");
        product.get().addImageString(UUID.randomUUID().toString() + "_" + "NEWIMAGE3.jpg");

        productRepository.save(product.orElseThrow());
    }

    @Test
    public void testList(){
        //상품과 상품 이미지 0번 대표이미지를 배열로 반환
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

}
