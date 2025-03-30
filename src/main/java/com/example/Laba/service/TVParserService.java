package com.example.Laba.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Laba.model.TVModel;
import com.example.Laba.repository.TVRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TVParserService {

    private static final String TARGET_URL = "https://comfy.ua/ua/flat-tvs/";

    @Autowired
    private TVRepository tvRepository;

    public List<TVModel> fetchProductDetails() {
        List<TVModel> tvList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(TARGET_URL)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                    .referrer("http://www.google.com")
                    .get();
            Elements productElements = document.select("div.prdl-item");

            for (Element productElement : productElements) {
                String title = productElement.select("a.prdl-item__name").text();
                String productPageUrl = "https://comfy.ua" + productElement.select("a.prdl-item__name").attr("href");
                String priceText = productElement.select("div.products-list-item-price__actions-price-current").text();
                float price = convertToFloat(priceText);
                String imageUrl = productElement.select("div.prdl-item__img img").attr("src");

                tvList.add(new TVModel(title, price, imageUrl, productPageUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tvRepository.saveAll(tvList);
        return tvList;
    }

    private float convertToFloat(String priceText) {
        try {
            String sanitizedPrice = priceText.replaceAll("[^0-9.,]", "").replace(",", ".");
            return Float.parseFloat(sanitizedPrice);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }
}
