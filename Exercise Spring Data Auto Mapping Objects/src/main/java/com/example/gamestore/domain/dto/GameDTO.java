package com.example.gamestore.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class GameDTO {
    private String title;
    private String trailer;
    private String thumbnailUrl;
    private float size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public GameDTO(String[] args) {
        this.title = args[1];
        this.price = new BigDecimal(args[2]);
        this.size = Float.parseFloat(args[3]);
        this.trailer = args[4];
        this.thumbnailUrl = args[5];
        this.description = args[6];
        this.releaseDate = LocalDate.parse(args[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static GameDTO createEditGameDTO(String[] args) {
        GameDTO gameDTO = new GameDTO();
        for (String arg : args) {
            if (arg.split("=")[0].equals("title")) {
                gameDTO.setTitle(arg.split("=")[1]);
            } else if (arg.split("=")[0].equals("trailer")) {
                gameDTO.setTrailer(arg.split("=")[1]);
            } else if (arg.split("=")[0].equals("thumbnailUrl")) {
                gameDTO.setThumbnailUrl(arg.split("=")[1]);
            } else if (arg.split("=")[0].equals("size")) {
                gameDTO.setSize(Float.parseFloat(arg.split("=")[1]));
            } else if (arg.split("=")[0].equals("price")) {
                gameDTO.setPrice(new BigDecimal(arg.split("=")[1]));
            } else if (arg.split("=")[0].equals("description")) {
                gameDTO.setDescription(arg.split("=")[1]);
            } else if (arg.split("=")[0].equals("releaseDate")) {
                gameDTO.setReleaseDate(LocalDate.parse(arg.split("=")[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        }
        return gameDTO;
    }
}
