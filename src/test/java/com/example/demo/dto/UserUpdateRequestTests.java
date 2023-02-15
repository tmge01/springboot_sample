package com.example.demo.dto;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootTest
@ActiveProfiles("test")
public class UserUpdateRequestTests {
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void ユーザーID桁数チェック() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid("test1234567890123456789012345");
        userUpdateRequest.setName("test1234567890123456789012345");
        userUpdateRequest.setEmail("aaa@example.com");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userUpdateRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "ユーザーIDは24桁以内で入力してください");
        });
    }

    @Test
    public void ユーザーID入力チェック() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid("");
        userUpdateRequest.setName("test1234567890123456789012345");
        userUpdateRequest.setEmail("aaa@example.com");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userUpdateRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "ユーザーIDを入力してください(必須)");
        });
    }

    @Test
    public void 名前桁数チェック() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid("test1");
        userUpdateRequest.setName("test1234567890123456789012345test123456789012345678901234512");
        userUpdateRequest.setEmail("aaa@example.com");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userUpdateRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "名前は50桁以内で入力してください");
        });
    } 

    @Test
    public void 名前入力チェック() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid("test1");
        userUpdateRequest.setName("");
        userUpdateRequest.setEmail("aaa@example.com");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userUpdateRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "名前を入力してください(必須)");
        });
    }

    @Test
    public void メールアドレスバリデーションチェック() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid("test1");
        userUpdateRequest.setName("テスト01");
        userUpdateRequest.setEmail("@");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userUpdateRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "メールアドレスの形式で入力してください");
        });
    } 

    @Test
    public void メールアドレス入力() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid("test1");
        userUpdateRequest.setName("テスト01");
        userUpdateRequest.setEmail("");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userUpdateRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "メールアドレスを入力してください(必須)");
        });
    } 
}
