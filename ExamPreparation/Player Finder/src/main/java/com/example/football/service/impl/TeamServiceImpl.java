package com.example.football.service.impl;

import com.example.football.models.dto.TeamSeedDTO;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TownService townService;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private static final String TEAMS_FILE_URI = "src/main/resources/files/json/teams.json";

    public TeamServiceImpl(TeamRepository teamRepository, TownService townService, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.teamRepository = teamRepository;
        this.townService = townService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_URI));
    }

    @Override
    public String importTeams() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TeamSeedDTO[] teamSeedDTOS = gson.fromJson(new FileReader(TEAMS_FILE_URI), TeamSeedDTO[].class);
        Arrays.stream(teamSeedDTOS)
                .filter(dto -> validateSeedTeamDto(sb, dto))
                .map(dto -> {
                    Team team = modelMapper.map(dto, Team.class);
                    team.setTown(townService.findByName(dto.getTownName()));
                    return team;
                }).forEach(teamRepository::save);
        return sb.toString().trim();
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name).orElse(null);
    }

    private boolean validateSeedTeamDto(StringBuilder sb, TeamSeedDTO dto) {
        boolean isValid;
        if (teamRepository.existsByName(dto.getName())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }
        sb.append(isValid ? String.format("Successfully imported Team %s - %s", dto.getName(), dto.getFanBase()) : "Invalid Team").append(System.lineSeparator());
        return isValid;
    }
}
