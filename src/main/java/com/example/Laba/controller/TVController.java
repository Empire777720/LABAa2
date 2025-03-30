package com.example.Laba.controller;

import com.example.Laba.model.TVModel;
import com.example.Laba.service.DollarService;
import com.example.Laba.service.ExcelService;
import com.example.Laba.service.TVParserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TVController {

    private final TVParserService parserService;
    private final DollarService dollarService;
    private final ExcelService excelService;

    public TVController(TVParserService parserService,
                        DollarService dollarService,
                        ExcelService excelService) {
        this.parserService = parserService;
        this.dollarService = dollarService;
        this.excelService = excelService;
    }

    @GetMapping
    public List<TVModel> getProducts() {
        List<TVModel> products = parserService.fetchProductDetails();
        return products;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadExcel() throws IOException {
        List<TVModel> TVModels = parserService.fetchProductDetails();
        float usdRate = dollarService.getUsdRate();
        File file = excelService.saveProductsToExcel(TVModels, usdRate);

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=TV.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}