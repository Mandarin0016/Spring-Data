package com.example.football.service;


import com.example.football.models.entity.Town;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface TownService {

    boolean areImported();

    String readTownsFileContent() throws IOException;

    String importTowns() throws FileNotFoundException;

    Town findByName(String townName);
}
