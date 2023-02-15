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
public class UserAddRequestTests {
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void ユーザーID桁数チェック() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("test1234567890123456789012345");
        userAddRequest.setName("test1234567890123456789012345");
        userAddRequest.setEmail("aaa@example.com");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userAddRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "ユーザーIDは24桁以内で入力してください");
        });
    }
    @Test
    public void ユーザーID入力チェック() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("");
        userAddRequest.setName("test1234567890123456789012345");
        userAddRequest.setEmail("aaa@example.com");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userAddRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "ユーザーIDを入力してください(必須)");
        });
    }

    @Test
    public void 名前桁数チェック() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("test1");
        userAddRequest.setName("test1234567890123456789012345test123456789012345678901234512");
        userAddRequest.setEmail("aaa@example.com");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userAddRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "名前は50桁以内で入力してください");
        });
    } 

    @Test
    public void 名前入力チェック() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("test1");
        userAddRequest.setName("");
        userAddRequest.setEmail("aaa@example.com");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userAddRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "名前を入力してください(必須)");
        });
    }

    @Test
    public void メールアドレスバリデーションチェック() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("test1");
        userAddRequest.setName("テスト01");
        userAddRequest.setEmail("@");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userAddRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "メールアドレスの形式で入力してください");
        });
    } 

    @Test
    public void メールアドレス入力() {
        UserAddRequest userAddRequest = new UserAddRequest();
        userAddRequest.setUserid("test1");
        userAddRequest.setName("テスト01");
        userAddRequest.setEmail("");
        Set<ConstraintViolation<UserAddRequest>> violations = validator.validate(userAddRequest);
        assertEquals(violations.size(),1);
        violations.stream().forEach(v ->{
            assertEquals(v.getMessage(), "メールアドレスを入力してください(必須)");
        });
    } 

    
}
