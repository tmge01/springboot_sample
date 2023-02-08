package com.example.demo.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.demo.entity.User;
import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserUpdateRequest;

@Mapper
public interface UserDao {
    /**
     * リスト型でユーザー一覧を返す
     */
    List<User> findAll();

    /**
     * ユーザー新規登録
     * @param userRequest　(新規登録用リクエストデータ)
     */
    void save(UserAddRequest userRequest);

    /**
     * ユーザーID検索
     * @param userid
     */
    User findById(String userid);

        /**
     * ユーザー情報更新
     * @param userUpdateRequest 更新用リクエストデータ
     */
    void update(UserUpdateRequest userUpdateRequest);

    /**
     * ユーザーの物理削除
     * @param userid ユーザーID
     */
    void delete(String userid);
}


