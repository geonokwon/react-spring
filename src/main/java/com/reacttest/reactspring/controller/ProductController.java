package com.reacttest.reactspring.controller;

import com.reacttest.reactspring.dto.PageRequestDTO;
import com.reacttest.reactspring.dto.PageResponseDTO;
import com.reacttest.reactspring.dto.ProductDTO;
import com.reacttest.reactspring.service.ProductService;
import com.reacttest.reactspring.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CustomFileUtil fileUtil;


    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO){
        log.info("list......................" + pageRequestDTO);
        return productService.getList(pageRequestDTO);
    }


    //등록기능 구현 POST 방식 사용 업로드 처리시 업로드된 파일의 숫자만큼 새로운 파일을 upload 폴더에 저장
    @PostMapping("/")
    public Map<String, Long> register(ProductDTO productDTO) {
        log.info("register: " + productDTO);

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = fileUtil.saveFile(files);

        productDTO.setUploadFileName(uploadFileNames);
        log.info(uploadFileNames);

        //서비스 호출
        Long pno = productService.register(productDTO);

        return Map.of("result", pno);
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        return fileUtil.getFile(fileName);
    }

    //조회
    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable Long pno){
        return productService.get(pno);
    }

    //수정
    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable Long pno, ProductDTO productDTO){
        productDTO.setPno(pno);
        ProductDTO oldProductDTO = productService.get(pno);

        //기존의 파일들(데이터베이스에 존재하는 파일들 - 수정 과정에서 삭제되었을 수 있음)
        List<String> oldFileNames = oldProductDTO.getUploadFileName();

        //새로 업로드 해야하는 파일들
        List<MultipartFile> files = productDTO.getFiles();

        //새로 업로드되어서 만들어진 파일 이름들
        List<String> currentUploadFileNames = fileUtil.saveFile(files);

        //화면에서 변화 없이 계속 유지된 파일들
        List<String> uploadFileNames = productDTO.getUploadFileName();

        //유지되는 파일들 + 새로 업로드된 파일 이름들이 저장해야 하는 파일 목록이 됨
        if(currentUploadFileNames != null && currentUploadFileNames.size() > 0){
            uploadFileNames.addAll(currentUploadFileNames);
        }

        //수정작업
        productService.modify(productDTO);

        if (oldFileNames != null && oldFileNames.size() > 0){
            //지워야 하는 파일 목록 찾기
            //예전 파일들 중에서 지워져야 하는 파일이름들
            List<String> removeFiles = oldFileNames.stream().filter(fileName -> uploadFileNames.indexOf(fileName) == -1)
                    .collect(Collectors.toList());

            //실제 파일 삭제
            fileUtil.deleteFile(removeFiles);
        }
        return Map.of("RESULT", "SUCCESS");
    }


    //삭제
    @DeleteMapping("/{pno}")
    public Map<String, String> delete(@PathVariable Long pno){
        //삭제해야할 파일들 조회
        List<String> oldFileName = productService.get(pno).getUploadFileName();
        productService.remove(pno);

        fileUtil.deleteFile(oldFileName);

        return Map.of("RESULT", "SUCCESS");
    }



}
