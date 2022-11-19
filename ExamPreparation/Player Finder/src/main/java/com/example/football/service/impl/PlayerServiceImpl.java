package com.example.football.service.impl;

import com.example.football.models.dto.PlayerRootSeedDTO;
import com.example.football.models.dto.PlayerSeedDTO;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;
    private final XmlParser xmlParser;
    private static final String PLAYERS_FILE_URI = "src/main/resources/files/xml/players.xml";

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, TownService townService, TeamService teamService, StatService statService, XmlParser xmlParser) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townService = townService;
        this.teamService = teamService;
        this.statService = statService;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_URI));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        PlayerRootSeedDTO rootDto = xmlParser.fromFile(PLAYERS_FILE_URI, PlayerRootSeedDTO.class);

        rootDto.getPlayerSeedDTO().stream().filter(dto -> validatePlayerSeedDto(sb, dto)).map(dto -> {
            Player player = modelMapper.map(dto, Player.class);
            player.setTown(townService.findByName(dto.getTownName().getName()));
            player.setTeam(teamService.findByName(dto.getTeamName().getName()));
            player.setStat(statService.findById(dto.getStatId().getId()));
            return player;
        }).forEach(playerRepository::save);

        return sb.toString().trim();
    }

    private boolean validatePlayerSeedDto(StringBuilder sb, PlayerSeedDTO dto) {
        boolean isValid;
        if (playerRepository.existsByEmail(dto.getEmail())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }
        sb.append(isValid ? String.format("Successfully imported Player %s %s - %s", dto.getFirstName(), dto.getLastName(), dto.getPosition().name()) : "Invalid Player").append(System.lineSeparator());
        return isValid;
    }

    @Override
    public String exportBestPlayers() {
        Collection<Player> theBestPlayers = playerRepository.findTheBestPlayers(LocalDate.of(1995, 1, 1), LocalDate.of(2003, 1, 1));
        return theBestPlayers.stream().map(String::valueOf).collect(Collectors.joining(System.lineSeparator()));
    }
}
