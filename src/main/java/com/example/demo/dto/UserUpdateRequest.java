package com.example.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Data;
/**
 *　バリデーションチェックは新規登録用を使う
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpdateRequest extends UserAddRequest {

}