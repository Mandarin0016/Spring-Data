package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserSeedDTO;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_FILE_PATH = "src/main/resources/files/users.json";
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserRepository userRepository;
    private final PictureService pictureService;

    public UserServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, UserRepository userRepository, PictureService pictureService) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userRepository = userRepository;
        this.pictureService = pictureService;
    }

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_FILE_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb = new StringBuilder();
        UserSeedDTO[] seedDTOS = gson.fromJson(new FileReader(USERS_FILE_PATH), UserSeedDTO[].class);

        Arrays.stream(seedDTOS)
                .filter(dto -> validateUserSeedDto(dto, sb))
                .map(dto -> {
                    User user = modelMapper.map(dto, User.class);
                    user.setProfilePicture(pictureService.findByPath(dto.getProfilePicture()));
                    return user;
                })
                .forEach(userRepository::save);

        return sb.toString().trim();
    }

    private boolean validateUserSeedDto(UserSeedDTO dto, StringBuilder sb) {
        boolean isValid;

        if (userRepository.existsByUsername(dto.getUsername()) || pictureService.findByPath(dto.getProfilePicture()) == null) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully imported User: %s", dto.getUsername()) : "Invalid User").append(System.lineSeparator());

        return isValid;
    }

    @Override
    public String exportUsersWithTheirPosts() {
        return null;
    }
}
