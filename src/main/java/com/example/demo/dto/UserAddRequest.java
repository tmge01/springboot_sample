package com.example.demo.dto;
import java.io.Serializable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Data;
/**
 * ユーザー情報登録 リクエストデータ
 */
@Data
public class UserAddRequest implements Serializable {
    /**
     * ユーザーID
     */
    @NotEmpty(message = "ユーザーIDを入力してください(必須)")
    @Size(max = 24, message = "ユーザーIDは24桁以内で入力してください")
    private String userid;

        /**
     * メールアドレス
     */
    @NotEmpty(message = "名前を入力してください(必須)")
    @Size(max = 50, message = "名前は50桁以内で入力してください")
    private String name;

    
    /**
     * 名前
     */
    @NotEmpty(message = "メールアドレスを入力してください(必須)")
    @Email(message = "メールアドレスの形式で入力してください")
    private String email;
}