package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.entity.User;
import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserUpdateRequest;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {
    @Autowired
    UserService userService;

    @Test
    void ユーザー一覧取得() {
        List<User> result = userService.findAll();
        int count = result.size();
        assertEquals(2,count);
    }

    @Test
    void ユーザー新規登録() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("newtestuser");
        userAddRequest.setName("テスト");
        userAddRequest.setEmail("aaa@example.com");

        int pre_count = userService.findAll().size();
        userService.save(userAddRequest);
        int after_count = userService.findAll().size();
        //*実行前より1件増えている */
        assertEquals(pre_count + 1,after_count);
        userService.delete("newtestuser");
    }
    
    @Test
    void ユーザー削除() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("deltestuser");
        userAddRequest.setName("テスト");
        userAddRequest.setEmail("aaa@example.com");
        userService.save(userAddRequest);
        //削除前ユーザー件数
        int pre_count = userService.findAll().size();
        userService.delete("deltestuser");
        //削除後ユーザー件数
        int after_count = userService.findAll().size();
        //*実行前より1件減っている */
        assertEquals(pre_count -1,after_count);
    }

    @Test
    void ユーザーID検索() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("searchuser");
        userAddRequest.setName("テスト");
        userAddRequest.setEmail("aaa@example.com");
        userService.save(userAddRequest);
        User result = userService.findById("searchuser");
        assertAll("ユーザー情報一致確認",
            () -> assertEquals("searchuser",result.getUserid(),"id一致"),
            () -> assertEquals("テスト",result.getName(),"名前一致"),
            () -> assertEquals("aaa@example.com",result.getEmail(),"メールアドレス一致")
        );
        userService.delete("searchuser");
        
    }

    @Test
    void ユーザー編集() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("updateuser");
        userAddRequest.setName("テスト");
        userAddRequest.setEmail("aaa@example.com");
        //1件追加
        userService.save(userAddRequest);
        //追加したユーザーを以下で編集
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid("updateuser");
        userUpdateRequest.setName("編集テスト");
        userUpdateRequest.setEmail("bbb@example.com");
        //更新
        userService.update(userUpdateRequest);

        User result = userService.findById("updateuser");
        assertAll("ユーザー情報一致確認",
            () -> assertEquals("updateuser",result.getUserid(),"id一致"),
            () -> assertEquals("編集テスト",result.getName(),"名前が変更されている"),
            () -> assertEquals("bbb@example.com",result.getEmail(),"メールアドレスが変更されている")
        );
        userService.delete("updateuser");
        
    }



    
}
