/*package ru.skypro.homework.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private AuthService authService;
    @SpyBean
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void setPassword() throws Exception {
        Long id = 1L;
        String email = "A";
        String password = "p";

        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("email", email);
        userObject.put("password", password);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setEmail(email);
        userInfo.setPassword(password);


        when(userRepository.findByEmail(email)).thenReturn(userInfo);

        mockMvc.perform(MockMvcRequestBuilders.post("/me")
                        .content(userInfo.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password));
    }

    @Test
    void updateUser() {
    }

    @Test
    void getUser_1() {
    }

    @Test
    void updateUserImage() {
    }
}*/