package com.example.demo.controller;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockService;

    @Test
    void ユーザー一覧画面が表示される() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("user/userlist"))
            .andReturn();
    }

    @Test
    void ユーザー新規登録画面が表示される() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("user/useradd"))
            .andReturn();
    }

    @Test
    void ユーザー編集画面が表示される() throws Exception {
        User user = new User();
        user.setUserid("test02");
        user.setName("テスト");
        user.setEmail("aaa@example.com");

        when(mockService.findById("test02")).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/test02/edit"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("user/useredit"))
            .andReturn();
    }

    @Test
    void ユーザー削除後にリダイレクトされている() throws Exception {
        User expect = new User();
        expect.setUserid("test02");
        expect.setName("テスト");
        expect.setEmail("aaa@example.com");

        mockMvc.perform(MockMvcRequestBuilders.get("/user/test02/delete"))            
            .andExpect(MockMvcResultMatchers.view().name("redirect:/"))
            .andReturn();
    }

    @Test
    void ユーザー新規登録時のバリデーション有の画面が正しい() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("user/useradd"))
            .andReturn();
    }

    @Test
    void ユーザー編集時のバリデーション有の画面が正しい() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("user/useredit"))
            .andReturn();
    }

    @Test
    void ユーザー新規登録時のバリデーション無しの画面が正しい() throws Exception {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("test11");
        userAddRequest.setName("テスト");
        userAddRequest.setEmail("aaa@example.com");
        mockMvc.perform((MockMvcRequestBuilders.post("/user/create")).flashAttr("userAddRequest",userAddRequest))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/"))
            .andReturn();
    }

    @Test
    void ユーザー編集時のバリデーション無しの画面が正しい() throws Exception {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid("test10");
        userUpdateRequest.setName("テスト1");
        userUpdateRequest.setEmail("aaaa@example.com");
        mockMvc.perform((MockMvcRequestBuilders.post("/user/update")).flashAttr("userUpdateRequest",userUpdateRequest))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/"))
            .andReturn();
    }
}





