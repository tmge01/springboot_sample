package com.example.demo.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class User implements Serializable {
    //ユーザーID
    private String userid;
    //名前
    private String name;
    //メールアドレス
    private String email;

}
