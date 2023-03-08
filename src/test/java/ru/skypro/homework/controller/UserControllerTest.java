/*package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.*;
import ru.skypro.homework.repository.*;
import ru.skypro.homework.service.*;
import ru.skypro.homework.service.impl.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@WithMockUser(roles = {"USER", "ADMIN", "PRE_VERIFICATION_USER"})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private CommentServiceImpl commentService;
    @SpyBean
    private ImageServiceImpl imageService;
    @SpyBean
    private AuthServiceImpl authService;
    @SpyBean
    private AdsServiceImpl adsServiceService;
    @MockBean
    private Authentication authentication;
    @SpyBean
    private UserServiceImpl userService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    CommentRepository commentRepository;
    @MockBean
    ImageRepository imageRepository;
    @MockBean
    AdsRepository adsRepository;
    @MockBean
    CreateAdsDtoMapper createAdsDtoMapper;
    @MockBean
    FullAdsMapper fullAdsMapper;
    @MockBean
    AdsMapper adsMapper;
    @MockBean
    SecurityService securityService;
    @InjectMocks
    private UserController userController;

    @Test
    void setPassword() throws Exception {
        Long id = 1L;
        String email = "user@gmail.com";
        String password = "password";

        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        Avatar avatar = new Avatar(1L, "a", 1L, "a", null, userInfo);
        UserDto userDto = userMapper.usertoUserDto(userInfo, avatar);

        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("email", email);
        userObject.put("password", password);


        when(userRepository.findByEmail(any(String.class))).thenReturn(userInfo);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userInfo));
        when(userRepository.save(any(UserInfo.class))).thenReturn(userInfo);

        when(adsRepository.findAllByUserInfoId(any(Long.class))).thenReturn();
        when(userRepository.save(any(UserInfo.class))).thenReturn(userInfo);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                        .with(csrf())
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password))
                .andReturn().getResponse().getContentAsString();
    }

}*/