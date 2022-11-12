package com.example.gamestore.service;

import com.example.gamestore.domain.dto.GameDTO;
import com.example.gamestore.domain.entity.Game;

public interface GameService {

    Game addGame(GameDTO addGameData);

    Game editGame(long id, GameDTO editGameData) throws IllegalAccessException;

    String deleteGame(long id);

    String getAll();

    String getByTitle(String title);

    String getOwnedGames();
}
