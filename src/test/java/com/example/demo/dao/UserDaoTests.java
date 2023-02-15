package com.example.demo.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;

@MybatisTest
@ActiveProfiles("test")
public class UserDaoTests {

    @Autowired
	private UserDao userDao;

    @Test
    void ユーザー一覧取得() {
        List<User> result = userDao.findAll();
        int count = result.size();
        assertAll("ユーザ情報取得",
        () -> assertEquals("test01",result.get(0).getUserid(),"id一致"),
        () -> assertEquals("テスト01",result.get(0).getName(),"名前一致"),
        () -> assertEquals("test01@example.com",result.get(0).getEmail(),"メール一致"),
        () -> assertEquals(2,count,"取得件数"));
    }

    @Test
    void ユーザー新規登録() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("newtestuser");
        userAddRequest.setName("テスト");
        userAddRequest.setEmail("aaa@example.com");

        userDao.save(userAddRequest);

        User result = userDao.findById("newtestuser");

        assertAll("新規ユーザー情報登録確認",
        () -> assertEquals("newtestuser",result.getUserid(),"id一致"),
        () -> assertEquals("テスト",result.getName(),"名前一致"),
        () -> assertEquals("aaa@example.com",result.getEmail(),"メール一致"));
        userDao.delete("newtestuser");
    }
    
    @Test
    void ユーザー削除() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("deltestuser");
        userAddRequest.setName("テスト");
        userAddRequest.setEmail("aaa@example.com");
        userDao.save(userAddRequest);
        //削除前ユーザー件数
        int pre_count = userDao.findAll().size();
        userDao.delete("deltestuser");
        //削除後ユーザー件数
        int after_count = userDao.findAll().size();
        //*実行前より1件減っている */
        assertEquals(pre_count -1,after_count);
    }

    @Test
    void ユーザーID検索() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("searchuser");
        userAddRequest.setName("テスト");
        userAddRequest.setEmail("aaa@example.com");
        userDao.save(userAddRequest);
        User result = userDao.findById("searchuser");
        assertAll("ユーザー情報一致確認",
            () -> assertEquals("searchuser",result.getUserid(),"id一致"),
            () -> assertEquals("テスト",result.getName(),"名前一致"),
            () -> assertEquals("aaa@example.com",result.getEmail(),"メールアドレス一致")
        );
        userDao.delete("searchuser");
        
    }

    @Test
    void ユーザー編集() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("updateuser");
        userAddRequest.setName("テスト");
        userAddRequest.setEmail("aaa@example.com");
        //1件追加
        userDao.save(userAddRequest);
        //追加したユーザーを以下で編集
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid("updateuser");
        userUpdateRequest.setName("編集テスト");
        userUpdateRequest.setEmail("bbb@example.com");
        //更新
        userDao.update(userUpdateRequest);

        User result = userDao.findById("updateuser");
        assertAll("ユーザー情報一致確認",
            () -> assertEquals("updateuser",result.getUserid(),"id一致"),
            () -> assertEquals("編集テスト",result.getName(),"名前が変更されている"),
            () -> assertEquals("bbb@example.com",result.getEmail(),"メールアドレスが変更されている")
        );
        userDao.delete("updateuser");
        
    }
}
