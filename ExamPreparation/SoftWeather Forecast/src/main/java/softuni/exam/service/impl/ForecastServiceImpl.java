package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastRootSeedDTO;
import softuni.exam.models.dto.ForecastSeedDTO;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final CityService cityService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private static final String FORECASTS_FILE_PATH = "src/main/resources/files/xml/forecasts.xml";
    private static final Integer SUNDAY_FORECAST_EXTRACT_CITIZEN_CRITERIA_NUMBER = 150000;

    public ForecastServiceImpl(ForecastRepository forecastRepository, CityService cityService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.forecastRepository = forecastRepository;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_FILE_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        ForecastRootSeedDTO rootDto = xmlParser.fromFile(FORECASTS_FILE_PATH, ForecastRootSeedDTO.class);

        rootDto.getForecasts().stream().filter(dto -> validateForecastSeedDto(sb, dto)).map(dto -> {
            Forecast forecast = modelMapper.map(dto, Forecast.class);
            forecast.setCity(cityService.findById(dto.getCityId()));
            return forecast;
        }).forEach(forecastRepository::save);

        return sb.toString().trim();
    }

    private boolean validateForecastSeedDto(StringBuilder sb, ForecastSeedDTO dto) {
        boolean isValid;

        if (dto.getDayOfWeek() == null || forecastRepository.existsForecastByDayOfWeekIsLikeAndCity_Id(dto.getDayOfWeek(), dto.getCityId())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully import forecast %s - %s", dto.getDayOfWeek().name(), dto.getMaxTemperature()) : "Invalid forecast").append(System.lineSeparator());
        return isValid;
    }

    @Override
    public String exportForecasts() {
        Collection<String> forecastList = forecastRepository.findSundayForecastForCitiesWithPopulationLessThan(SUNDAY_FORECAST_EXTRACT_CITIZEN_CRITERIA_NUMBER).stream().map(String::valueOf).toList();
        return String.join(System.lineSeparator(), forecastList);
    }
}
