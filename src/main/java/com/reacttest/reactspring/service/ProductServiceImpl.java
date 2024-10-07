package com.reacttest.reactspring.service;

import com.reacttest.reactspring.domain.Product;
import com.reacttest.reactspring.domain.ProductImage;
import com.reacttest.reactspring.dto.PageRequestDTO;
import com.reacttest.reactspring.dto.PageResponseDTO;
import com.reacttest.reactspring.dto.ProductDTO;
import com.reacttest.reactspring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    //ProductRepository 를 통해서 Page(Object[])의 타입의 결과 데이터 가져오기
    //각 Object[] 의 내용물은 Product 객체와 ProductImage 객체임
    //반복처리로 Product 와 ProductImage 를 ProductDTO 타입으로 변환
    //변환된 ProductDTO 를 List(ProductDTO)로 처리하고 전체 데이터 개수를 이용해서 PageResponseDTO 타입으로 생성하고 반환

    private final ProductRepository productRepository;

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

        log.info("getList .....................");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, //페이지 시작번호가 0부터 시작하므로 -1 처리
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);
        List<ProductDTO> dtoList = result.get().map(arr -> {
            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            ProductDTO productDTO = ProductDTO.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .price(product.getPrice())
                    .build();
            String imageStr = productImage.getFileName();
            productDTO.setUploadFileName(List.of(imageStr));
            return productDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();
        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }


    @Override
    public Long register(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);

        Product result = productRepository.save(product);

        return result.getPno();
    }

    private Product dtoToEntity(ProductDTO productDTO) {
        Product product = Product.builder()
                .pno(productDTO.getPno())
                .pname(productDTO.getPname())
                .pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice())
                .build();

        //업로드 처리가 끝난 파일들의 이름 리스트
        List<String> uploadFileName = productDTO.getUploadFileName();
        if (uploadFileName == null){
            return product;
        }
        for (String uploadName : uploadFileName) {
            product.addImageString(uploadName);
        }

        return product;
    }


    @Override
    public ProductDTO get(Long pno) {
        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        ProductDTO productDTO = entityToDTO(product);

        return productDTO;
    }

    private ProductDTO entityToDTO(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .price(product.getPrice())
                .build();

        List<ProductImage> imageList = product.getImageList();

        if(imageList == null || imageList.size() == 0){
            return productDTO;
        }

        List<String> fileNameList = imageList.stream().map(productImage ->
                productImage.getFileName()).toList();

        productDTO.setUploadFileName(fileNameList);

        return productDTO;
    }


    @Override
    public void modify(ProductDTO productDTO) {
        //read
        Optional<Product> result = productRepository.findById(productDTO.getPno());

        Product product = result.orElseThrow();

        //change pname, pdesc, price
        product.changeName(productDTO.getPname());
        product.changeDesc(productDTO.getPdesc());
        product.changePrice(productDTO.getPrice());

        //uploadFile -- clear
        product.clearList();

        List<String> uploadFileName = productDTO.getUploadFileName();
        if (uploadFileName != null && uploadFileName.size() > 0){
            uploadFileName.stream().forEach(uploadName -> {
                product.addImageString(uploadName);
            });
        }
        productRepository.save(product);
    }


    @Override
    public void remove(Long pno) {
        productRepository.updateToDelete(pno, true);
    }
}
