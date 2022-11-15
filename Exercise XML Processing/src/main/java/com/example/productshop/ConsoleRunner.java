package com.example.productshop;

import com.example.productshop.model.dto.CategoriesSeedDTO;
import com.example.productshop.service.CategoryService;
import com.example.productshop.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final String RESOURCE_FILE_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private final XmlParser xmlParser;
    private final CategoryService categoryService;

    public ConsoleRunner(XmlParser xmlParser, CategoryService categoryService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() throws JAXBException, FileNotFoundException {
        CategoriesSeedDTO categoriesSeedDTO = xmlParser.fromFile(RESOURCE_FILE_PATH.concat(CATEGORIES_FILE_NAME), CategoriesSeedDTO.class);

        categoryService.seedCollection(categoriesSeedDTO.getCategories());
    }
}
