package ru.skypro.homework.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.*;
import ru.skypro.homework.repository.*;
import ru.skypro.homework.service.impl.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdsController.class)
@AutoConfigureMockMvc
@WithMockUser(roles = {"USER", "ADMIN", "PRE_VERIFICATION_USER"})
public class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AdsRepository adsRepository;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private AdsServiceImpl adsService;

    @SpyBean
    private ImageServiceImpl imageService;

    @SpyBean
    private CommentServiceImpl commentService;

    @SpyBean
    private UserServiceImpl userService;

    @MockBean
    private SecurityServiceImpl securityService;

    @SpyBean
    private CommentMapperImpl commentMapper;

    @SpyBean
    AdsMapperImpl adsMapper;

    @SpyBean
    private CreateAdsDtoMapperImpl createAdsDtoMapper;

    @SpyBean
    private FullAdsMapperImpl fullAdsMapper;

    @SpyBean
    private UserMapperImpl userMapper;

    @Test
    public void testAds() throws Exception {

        final Long adsPk = 1L;
        final String adsDescription = "description";
        final String adsTitle = "title";
        final Long adsPrice = 1000L;

        final String userMail = "user@Mail";
        final String userFirstName = "firstName";
        final Long userId = 2L;
        final String userLastName = "lastName";
        final String userPhone = "88005553322";
        final String userRegDate = "01.01.2022";
        final String userCity = "Moscow";
        final String userPassword = "password";

        final String commentCreatedAt = "04.03.2023";
        final Long commentPk = 3L;
        final String commentText = "text";

        final Long imageId = 4L;
        final String imageFilePath = "filePath";
        final long imageFileSize = 1000L;
        final String imageMediaType = "image.Jpg";
        final byte[] imageData = new byte[]{(byte) 0x80, 0x53, 0x1c,
                (byte) 0x87, (byte) 0xa0, 0x42, 0x69, 0x10, (byte) 0xa2, (byte) 0xea, 0x08};

        UserInfo userInfo = new UserInfo(userMail, userFirstName, userId,
                userLastName, userPhone, userRegDate, userCity, userPassword);

        Ads ads = new Ads();
        Comment comment = new Comment(userInfo, commentCreatedAt, commentPk, commentText, ads);
        Image image = new Image(imageId, imageFilePath, imageFileSize, imageMediaType, imageData, ads);

        List<Comment> commentList = new ArrayList<>(List.of(comment));

        List<Image> imageList = new ArrayList<>(List.of(image));

        ads.setPk(adsPk);
        ads.setDescription(adsDescription);
        ads.setTitle(adsTitle);
        ads.setUserInfo(userInfo);
        ads.setComments(commentList);
        ads.setImage(imageList);
        ads.setPrice(adsPrice);

        JSONObject adsObject = new JSONObject();
        adsObject.put("description", adsDescription);
        adsObject.put("title", adsTitle);
        adsObject.put("price", adsPrice);


        Mockito.when(adsRepository.save(any(Ads.class))).thenReturn(ads);
        Mockito.when(adsRepository.findById(any(Long.class))).thenReturn(Optional.of(ads));

        Mockito.when(userRepository.save(any(UserInfo.class))).thenReturn(userInfo);
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userInfo));

        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        Mockito.when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment));

        Mockito.when(imageRepository.save(any(Image.class))).thenReturn(image);
        Mockito.when(imageRepository.findById(any(Long.class))).thenReturn(Optional.of(image));

        byte[] bytes = Files.readAllBytes(Paths.get(AdsControllerTest.class.getResource("test.jpg").toURI()));
        MockMultipartFile imagePart = new MockMultipartFile("image", bytes);
        MockMultipartFile jsonPart = new MockMultipartFile(
                "properties", "",
                MediaType.APPLICATION_JSON_VALUE,
                adsObject.toString().getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/ads")
                        .file(imagePart)
                        .file(jsonPart)
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adsPk").value(adsPk))
                .andExpect(jsonPath("$.adsDescription").value(adsDescription))
                .andExpect(jsonPath("$.adsTitle").value(adsTitle))
                .andExpect(jsonPath("$.userInfo").value(userInfo))
                .andExpect(jsonPath("$.commentList").value(commentList))
                .andExpect(jsonPath("$.imageList").value(imageList))
                .andExpect(jsonPath("$.adsPrise").value(adsPrice));
    }

}
