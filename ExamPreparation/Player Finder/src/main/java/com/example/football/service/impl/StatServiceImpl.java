package com.example.football.service.impl;

import com.example.football.models.dto.StatRootSeedDTO;
import com.example.football.models.dto.StatSeedDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private static final String STATS_FILE_URI = "src/main/resources/files/xml/stats.xml";

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_FILE_URI));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        StatRootSeedDTO rootDto = xmlParser.fromFile(STATS_FILE_URI, StatRootSeedDTO.class);

        rootDto.getStatSeedDTOS().stream()
                .filter(statSeedDTO -> validateStatSeedDto(sb, statSeedDTO))
                .map(dto -> modelMapper.map(dto, Stat.class))
                .forEachOrdered(statRepository::save);

        return sb.toString().trim();
    }

    @Override
    public Stat findById(Long id) {
        return statRepository.findById(id).orElse(null);
    }

    private boolean validateStatSeedDto(StringBuilder sb, StatSeedDTO statSeedDTO) {
        boolean isValid;
        if (statRepository.existsByPassingAndShootingAndEndurance(statSeedDTO.getPassing(), statSeedDTO.getShooting(), statSeedDTO.getEndurance())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(statSeedDTO);
        }
        sb.append(isValid ? String.format("Successfully imported Stat %s - %s - %s", statSeedDTO.getShooting(), statSeedDTO.getPassing(), statSeedDTO.getEndurance()) : "Invalid Stat").append(System.lineSeparator());
        return isValid;
    }
}
