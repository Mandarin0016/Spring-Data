package com.example.productshop.service;

import com.example.productshop.model.dto.ProductSeedDTO;
import com.example.productshop.model.dto.ProductsWithSellerDTO;
import com.example.productshop.model.entity.User;

import java.util.List;

public interface ProductService {
    void seedDtoCollection(List<ProductSeedDTO> productSeedDTOList);

    ProductsWithSellerDTO getProductsInRangeWithoutBuyer();
}
