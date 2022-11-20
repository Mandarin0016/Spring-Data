package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private static final String COUNTRIES_FILE_PATH = "src/main/resources/files/json/countries.json";

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();
        CountrySeedDTO[] countriesDTOs = gson.fromJson(new FileReader(COUNTRIES_FILE_PATH), CountrySeedDTO[].class);

        Arrays.stream(countriesDTOs).filter(dto -> validateCountrySeedDTO(sb, dto)).map(dto -> modelMapper.map(dto, Country.class)).forEach(countryRepository::save);

        return sb.toString().trim();
    }

    @Override
    public Country findById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    private boolean validateCountrySeedDTO(StringBuilder sb, CountrySeedDTO dto) {
        boolean isValid;

        if (countryRepository.existsByCountryName(dto.getCountryName())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully imported country %s - %s", dto.getCountryName(), dto.getCurrency()) : "Invalid country").append(System.lineSeparator());
        return isValid;
    }
}
