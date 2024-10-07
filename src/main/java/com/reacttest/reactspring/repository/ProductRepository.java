package com.reacttest.reactspring.repository;

import com.reacttest.reactspring.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Objects;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //EntityGraph 를 이용해서 쿼리 실행횟수를 줄일수 있다 해당속성을 조인처리 하도록 설정
    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.pno = :pno")
    Optional<Product> selectOne(@Param("pno") Long pno);

    //삭제(수정)을 위한 Modifying 사용
    @Modifying
    @Query("update Product p set p.delFlag = :flag where p.pno = :pno")
    void updateToDelete(@Param("pno") Long pno, @Param("flag") boolean flag);

    //목록화면 상품당 하나의 이미지가 포함되는 형태로 처리되어야함
    //ord값이 0인 상품의 대표이지미들만 처리해서 출력하도록 구성
    @Query("select p, pi from Product p left join p.imageList pi where pi.ord = 0 and p.delFlag = false ")
    Page<Object[]> selectList (Pageable pageable);

}
