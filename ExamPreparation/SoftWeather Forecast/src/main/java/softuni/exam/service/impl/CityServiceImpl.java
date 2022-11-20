package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CitySeedDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryService countryService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private static final String CITIES_FILE_PATH = "src/main/resources/files/json/cities.json";

    public CityServiceImpl(CityRepository cityRepository, CountryService countryService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.cityRepository = cityRepository;
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override

    public boolean areImported() {
        return cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();
        CitySeedDTO[] citiesDTOs = gson.fromJson(new FileReader(CITIES_FILE_PATH), CitySeedDTO[].class);

        Arrays.stream(citiesDTOs)
                .filter(dto -> validateCitySeedDTO(sb, dto))
                .map(dto -> {
                    City city = modelMapper.map(dto, City.class);
                    city.setCountry(countryService.findById(dto.getCountry()));
                    return city;
                })
                .forEach(cityRepository::save);

        return sb.toString().trim();
    }

    @Override
    public City findById(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

    private boolean validateCitySeedDTO(StringBuilder sb, CitySeedDTO dto) {
        boolean isValid;

        if (cityRepository.existsByCityName(dto.getCityName())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully imported city %s - %s", dto.getCityName(), dto.getPopulation()) : "Invalid city").append(System.lineSeparator());
        return isValid;
    }
}
