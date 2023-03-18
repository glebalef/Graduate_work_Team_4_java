package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.UserInfo;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AuthServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@WithMockUser(roles = {"USER", "ADMIN", "PRE_VERIFICATION_USER"})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private AuthServiceImpl authService;
    @SpyBean
    private UserServiceImpl userService;
    @MockBean
    private UserMapper userMapper;
    @InjectMocks
    private UserController userController;
    @Test
    void testUser() throws Exception {
        Long id = 1L;
        String email = "user@gmail.com";
        String password = "password";
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setEmail(email);
        userInfo.setPassword(password);

        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setEmail(email);

        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setCurrentPassword("password");
        newPasswordDto.setNewPassword("newPassword");

        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("email", email);
        userObject.put("password", password);

        when(userRepository.findByEmail(any(String.class))).thenReturn(userInfo);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userInfo));
        when(userService.updateUser(userDto, email)).thenReturn(userDto);
        when(userService.getUser(email)).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                        .with(csrf())
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value(email));

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/me")
                        .with(csrf())
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value(email));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/set_password")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newPasswordDto)))
                .andExpect(status().isOk());

    }

}
