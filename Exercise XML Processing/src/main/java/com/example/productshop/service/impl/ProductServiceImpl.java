package com.example.productshop.service.impl;

import com.example.productshop.model.dto.ProductSeedDTO;
import com.example.productshop.model.dto.ProductWithSellerDTO;
import com.example.productshop.model.dto.ProductsWithSellerDTO;
import com.example.productshop.model.entity.Product;
import com.example.productshop.repository.ProductRepository;
import com.example.productshop.service.CategoryService;
import com.example.productshop.service.ProductService;
import com.example.productshop.service.UserService;
import com.example.productshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedDtoCollection(List<ProductSeedDTO> productSeedDTOList) {
        if (productRepository.count() == 0) {
            productSeedDTOList.stream()
                    .filter(validationUtil::isValid)
                    .map(this::prepareProductBasedOnDto)
                    .forEach(productRepository::save);
        }
    }

    @Override
    public ProductsWithSellerDTO getProductsInRangeWithoutBuyer() {
        ProductsWithSellerDTO productsWithSellerDTO = new ProductsWithSellerDTO();
        productsWithSellerDTO.setProductWithSellerDTOList(productRepository
                .findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L))
                .stream()
                .map(product -> {
                    ProductWithSellerDTO dto = modelMapper.map(product, ProductWithSellerDTO.class);
                    dto.setSeller(String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName()));
                    return dto;
                })
                .collect(Collectors.toList()));
        return productsWithSellerDTO;
    }

    private Product prepareProductBasedOnDto(ProductSeedDTO pDto) {
        Product product = modelMapper.map(pDto, Product.class);
        product.setSeller(userService.getRandom());
        if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0) {
            product.setBuyer(userService.getRandom());
        }
        product.setCategories(categoryService.getRandomSet());
        return product;
    }
}
