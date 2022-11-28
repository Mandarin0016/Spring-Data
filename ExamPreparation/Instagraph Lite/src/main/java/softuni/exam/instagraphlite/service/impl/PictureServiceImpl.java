package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureSeedDTO;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURES_FILE_PATH = "src/main/resources/files/pictures.json";
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, PictureRepository pictureRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();
        PictureSeedDTO[] seedDTOS = gson.fromJson(new FileReader(PICTURES_FILE_PATH), PictureSeedDTO[].class);

        Arrays.stream(seedDTOS)
                .filter(dto -> validatePictureSeedDto(dto, sb))
                .map(dto -> modelMapper.map(dto, Picture.class))
                .forEach(pictureRepository::save);

        return sb.toString().trim();
    }

    private boolean validatePictureSeedDto(PictureSeedDTO dto, StringBuilder sb) {
        boolean isValid;

        if (pictureRepository.existsByPath(dto.getPath())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully imported Picture, with size %.2f", dto.getSize()) : "Invalid Picture").append(System.lineSeparator());

        return isValid;
    }

    @Override
    public String exportPictures() {
        return null;
    }

    @Override
    public Picture findByPath(String path) {
        return pictureRepository.findByPath(path).orElse(null);
    }
}
