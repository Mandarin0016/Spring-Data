package com.example.productshop;

import com.example.productshop.model.dto.*;
import com.example.productshop.service.CategoryService;
import com.example.productshop.service.ProductService;
import com.example.productshop.service.UserService;
import com.example.productshop.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final String RESOURCE_FILE_PATH = "src/main/resources/files/";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/files/out/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";
    private static final String PRODUCTS_IN_RANGE_FILE_NAME = "products-in-range.xml";
    private static final String SOLD_PRODUCTS_FILE_NAME = "users-sold-products.xml";
    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final Scanner scanner;

    public ConsoleRunner(XmlParser xmlParser, CategoryService categoryService, UserService userService, ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Select exercise number: ");
        int exNumber = Integer.parseInt(scanner.nextLine());

        switch (exNumber) {
            case 1 -> productsInRange();
            case 2 -> successfullySoldProducts();
        }
    }


    private void successfullySoldProducts() throws JAXBException {
        UsersWithSoldProductsDTO usersWithSoldProductsDTO = userService
                .getAllWithMoreThanOneSoldProduct();
        xmlParser.toFile(OUTPUT_FILE_PATH.concat(SOLD_PRODUCTS_FILE_NAME), usersWithSoldProductsDTO);
    }

    private void productsInRange() throws JAXBException {
        ProductsWithSellerDTO products = productService
                .getProductsInRangeWithoutBuyer();
        xmlParser.toFile(OUTPUT_FILE_PATH.concat(PRODUCTS_IN_RANGE_FILE_NAME), products);
    }

    private void seedData() throws JAXBException, FileNotFoundException {
        CategoriesSeedDTO categoriesSeedDTO = xmlParser.fromFile(RESOURCE_FILE_PATH.concat(CATEGORIES_FILE_NAME), CategoriesSeedDTO.class);
        categoryService.seedDtoCollection(categoriesSeedDTO.getCategories());
        UsersSeedDTO usersSeedDTO = xmlParser.fromFile(RESOURCE_FILE_PATH.concat(USERS_FILE_NAME), UsersSeedDTO.class);
        userService.seedDtoCollection(usersSeedDTO.getUserSeedDTOList());
        ProductsSeedDTO productsSeedDTO = xmlParser.fromFile(RESOURCE_FILE_PATH.concat(PRODUCTS_FILE_NAME), ProductsSeedDTO.class);
        productService.seedDtoCollection(productsSeedDTO.getProductSeedDTOList());
    }
}
