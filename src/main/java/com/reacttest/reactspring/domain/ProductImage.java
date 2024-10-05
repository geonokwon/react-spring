package com.reacttest.reactspring.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable //해당 인스턴스가 값 타입의 객체임을 명시
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private String fileName;

    //순서 속성을 가지도록 만듬 (각 이미지마다 번호를 지정하고 상품 목록을 출력할 떄
    //ord 값이 0번인 이미지들만 화면에 볼수 있게 처리
    private int ord;

    public void setOrd(int ord){
        this.ord = ord;
    }
}
