package com.example.gamestore.init;

import com.example.gamestore.domain.dto.GameDTO;
import com.example.gamestore.domain.dto.LoginDTO;
import com.example.gamestore.domain.dto.RegisterDTO;
import com.example.gamestore.domain.entity.Game;
import com.example.gamestore.domain.entity.User;
import com.example.gamestore.service.GameService;
import com.example.gamestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.example.gamestore.domain.dto.GameDTO.createEditGameDTO;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private final String REGISTER_USER_COMMAND = "RegisterUser";
    private final String LOGIN_USER_COMMAND = "LoginUser";
    private final String LOGOUT_USER_COMMAND = "Logout";
    private final String ADD_GAME_COMMAND = "AddGame";
    private final String EDIT_GAME_COMMAND = "EditGame";
    private final String DELETE_GAME_COMMAND = "DeleteGame";
    private final String ALL_GAMES_VIEW_COMMAND = "AllGames";
    private final String GAME_DETAILS_VIEW_COMMAND = "DetailGame";
    private final String OWNED_GAMES_VIEW_COMMAND = "OwnedGames";
    private final String UNKNOWN_COMMAND = "Unknown command";

    @Autowired
    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    private String execute(String command) throws IllegalAccessException {
        String[] args = command.split("\\|");

        String commandName = args[0];

        return switch (commandName) {
            case REGISTER_USER_COMMAND -> {
                RegisterDTO registerData = new RegisterDTO(args);
                User user = userService.register(registerData);
                yield String.format("%s was registered%n", user.getFullName());
            }
            case LOGIN_USER_COMMAND -> {
                LoginDTO loginData = new LoginDTO(args);
                User user = userService.login(loginData);
                yield String.format("Successfully logged in %s%n", user.getFullName());
            }
            case LOGOUT_USER_COMMAND -> userService.logout();
            case ADD_GAME_COMMAND -> {
                GameDTO addGameData = new GameDTO(args);
                Game game = gameService.addGame(addGameData);
                yield String.format("Added %s", game.getTitle());
            }
            case EDIT_GAME_COMMAND -> {
                GameDTO editGameData = createEditGameDTO(args);
                Game game = gameService.editGame(Long.parseLong(args[1]), editGameData);
                yield String.format("Edited %s", game.getTitle());
            }
            case DELETE_GAME_COMMAND -> gameService.deleteGame(Long.parseLong(args[1]));
            case ALL_GAMES_VIEW_COMMAND -> gameService.getAll();
            case GAME_DETAILS_VIEW_COMMAND -> gameService.getByTitle(args[1]);
            case OWNED_GAMES_VIEW_COMMAND -> gameService.getOwnedGames();
            default -> UNKNOWN_COMMAND;
        };
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String data = execute(scanner.nextLine());
        System.out.println(data);

        while (!data.equals(UNKNOWN_COMMAND) && !data.equals(LOGOUT_USER_COMMAND)) {
            data = scanner.nextLine();
            System.out.println(execute(data));
        }
    }
}
