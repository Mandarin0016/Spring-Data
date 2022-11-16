package com.example.productshop.service;

import com.example.productshop.model.dto.UserSeedDTO;
import com.example.productshop.model.dto.UsersWithSoldProductsDTO;
import com.example.productshop.model.entity.User;

import java.util.List;

public interface UserService {
    void seedDtoCollection(List<UserSeedDTO> userSeedDTOList);

    User getRandom();

    UsersWithSoldProductsDTO getAllWithMoreThanOneSoldProduct();
}
