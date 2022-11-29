package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PostRootSeedDTO;
import softuni.exam.instagraphlite.models.dto.PostSeedDTO;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final PictureService pictureService;
    private final UserService userService;
    private final static String POSTS_FILE_PATH = "src/main/resources/files/posts.xml";

    public PostServiceImpl(PostRepository postRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, PictureService pictureService, UserService userService) {
        this.postRepository = postRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
        this.userService = userService;
    }

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_FILE_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        PostRootSeedDTO rootDto = xmlParser.fromFile(POSTS_FILE_PATH, PostRootSeedDTO.class);

        rootDto.getPosts().stream()
                .filter(dto -> validatePostSeedDto(dto, sb))
                .map(dto -> {
                    Post post = modelMapper.map(dto, Post.class);
                    post.setPicture(pictureService.findByPath(dto.getPicture().getPath()));
                    post.setUser(userService.findByUsername(dto.getUser().getUsername()));
                    return post;
                })
                .forEach(postRepository::save);

        return sb.toString().trim();
    }

    private boolean validatePostSeedDto(PostSeedDTO dto, StringBuilder sb) {
        boolean isValid;

        if (!pictureService.existByPath(dto.getPicture().getPath()) || !userService.existByUsername(dto.getUser().getUsername())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully imported Post, made by %s", dto.getUser().getUsername()) : "Invalid Post").append(System.lineSeparator());

        return isValid;
    }
}
