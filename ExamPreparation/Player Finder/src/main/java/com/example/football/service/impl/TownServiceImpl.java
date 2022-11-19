package com.example.football.service.impl;

import com.example.football.models.dto.TownSeedDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private static final String TOWNS_FILE_URI = "src/main/resources/files/json/towns.json";

    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_URI));
    }

    @Override
    public String importTowns() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TownSeedDTO[] townSeedDTOS = gson.fromJson(new FileReader(TOWNS_FILE_URI), TownSeedDTO[].class);
        Arrays.stream(townSeedDTOS)
                .filter(dto -> validateSeedTownDto(sb, dto))
                .map(townSeedDTO -> modelMapper.map(townSeedDTO, Town.class))
                .forEach(townRepository::save);
        return sb.toString().trim();
    }

    @Override
    public Town findByName(String townName) {
        return townRepository.findByName(townName).orElse(null);
    }

    private boolean validateSeedTownDto(StringBuilder sb, TownSeedDTO dto) {
        boolean isValid;
        if (townRepository.existsByName(dto.getName())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }
        sb.append(isValid ? String.format("Successfully imported Town %s - %s", dto.getName(), dto.getPopulation()) : "Invalid Town").append(System.lineSeparator());
        return isValid;
    }
}
