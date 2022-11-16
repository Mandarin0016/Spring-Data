package com.example.productshop.service.impl;

import com.example.productshop.model.dto.SoldProductDTO;
import com.example.productshop.model.dto.UserSeedDTO;
import com.example.productshop.model.dto.UserWithSoldProductsDTO;
import com.example.productshop.model.dto.UsersWithSoldProductsDTO;
import com.example.productshop.model.entity.User;
import com.example.productshop.repository.UserRepository;
import com.example.productshop.service.UserService;
import com.example.productshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedDtoCollection(List<UserSeedDTO> userSeedDTOList) {
        if (userRepository.count() == 0) {
            userSeedDTOList.stream()
                    .filter(validationUtil::isValid)
                    .map(uDto -> modelMapper.map(uDto, User.class))
                    .forEach(userRepository::save);
        }
    }

    @Override
    public User getRandom() {
        long randomId = ThreadLocalRandom
                .current()
                .nextLong(1, userRepository.count() + 1);
        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public UsersWithSoldProductsDTO getAllWithMoreThanOneSoldProduct() {
        UsersWithSoldProductsDTO rootDto = new UsersWithSoldProductsDTO();
        rootDto.setUserWithSoldProductsDTOList(userRepository.findAllUsersWithMoreThanOneSoldProducts()
                .stream().map(user -> {
                    UserWithSoldProductsDTO dto = modelMapper.map(user, UserWithSoldProductsDTO.class);
                    dto.setSoldProductDTOList(user.getSoldProducts().stream().map(p -> modelMapper.map(p, SoldProductDTO.class)).collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList()));
        return rootDto;
    }
}
