package com.reacttest.reactspring.controller;

import com.reacttest.reactspring.dto.ProductDTO;
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

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;

    //등록기능 구현 POST 방식 사용 업로드 처리시 업로드된 파일의 숫자만큼 새로운 파일을 upload 폴더에 저장
    @PostMapping("/")
    public Map<String, String> register(ProductDTO productDTO) {
        log.info("register: " + productDTO);

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = fileUtil.saveFile(files);

        productDTO.setUploadFileName(uploadFileNames);

        log.info("RESULT: " + "SUCCESS");
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        return fileUtil.getFile(fileName);
    }




}
