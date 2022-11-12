package com.example.gamestore.service.impl;

import com.example.gamestore.domain.dto.GameDTO;
import com.example.gamestore.domain.entity.Game;
import com.example.gamestore.domain.entity.User;
import com.example.gamestore.repository.GameRepository;
import com.example.gamestore.service.GameService;
import com.example.gamestore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }


    @Override
    public Game addGame(GameDTO addGameData) {
        validateAdminUser();
        Game game = modelMapper.map(addGameData, Game.class);
        return gameRepository.save(game);
    }

    private void validateAdminUser() {
        if (userService.getLoggedInUser() == null) {
            throw new IllegalArgumentException("Please login with admin account");
        } else if (!userService.getLoggedInUser().isAdmin()) {
            throw new IllegalArgumentException("You are not admin!");
        }
    }

    @Override
    public Game editGame(long id, GameDTO editGameData) throws IllegalAccessException {
        validateAdminUser();
        Game game = gameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Game with the given ID does not exist."));
        for (Field field : editGameData.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(editGameData) != null) {
                switch (field.getName()) {
                    case "title" -> game.setTitle((String) field.get(editGameData));
                    case "trailer" -> game.setTrailer((String) field.get(editGameData));
                    case "thumbnailUrl" -> game.setThumbnailUrl((String) field.get(editGameData));
                    case "size" -> game.setSize((Float) field.get(editGameData));
                    case "price" -> game.setPrice((BigDecimal) field.get(editGameData));
                    case "description" -> game.setDescription((String) field.get(editGameData));
                    case "releaseDate" -> game.setReleaseDate((LocalDate) field.get(editGameData));
                }
            }
        }
        return gameRepository.save(game);
    }

    @Override
    public String deleteGame(long id) {
        validateAdminUser();
        Game game = gameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Game with the given ID does not exist."));
        gameRepository.deleteById(id);
        return String.format("Deleted %s", game.getTitle());
    }

    @Override
    public String getAll() {
        return gameRepository.getAllTitlesAndPrices().stream().collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String getByTitle(String title) {
        Game game = gameRepository.findByTitle(title).orElseThrow(() -> new IllegalArgumentException("Game with the given title does not exist."));
        return game.toString();
    }

    @Override
    public String getOwnedGames() {
        User user = userService.getLoggedInUser();
        if (user == null) {
            throw new IllegalArgumentException("No logged in user found.");
        }
        return user.getGames().stream().map(Game::getTitle).collect(Collectors.joining(System.lineSeparator()));
    }
}
