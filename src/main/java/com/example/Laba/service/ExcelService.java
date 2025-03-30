package com.example.Laba.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.example.Laba.model.TVModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public File saveProductsToExcel(List<TVModel> tvModels, float currency) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TV");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Title");
        header.createCell(1).setCellValue("Price in UAH");
        header.createCell(2).setCellValue("Price in USD");
        header.createCell(3).setCellValue("Image URL");
        header.createCell(4).setCellValue("Product Link");

        int rowNum = 1;
        for (TVModel tvModel : tvModels) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(tvModel.getTitle());
            row.createCell(1).setCellValue(tvModel.getPrice());
            float dollarPrice = tvModel.getPrice() / currency;
            row.createCell(2).setCellValue(dollarPrice);
            row.createCell(3).setCellValue(tvModel.getImageUrl());
            row.createCell(4).setCellValue(tvModel.getProductUrl());
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        File file = new File(System.getProperty("java.io.tmpdir") + "TV.xlsx");
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Data saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}