package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserUpdateRequest;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * ユーザ全検索
     * @param userAddRequest リクエストデータ
     */
    public List<User> findAll(){
        return userDao.findAll();
    }

    /**
     * ユーザ新規登録
     * @param userAddRequest リクエストデータ
     */
    public void save(UserAddRequest userAddRequest) {
        userDao.save(userAddRequest);
    }
    
    /**
     * ユーザー物理削除
     * @param userid
     */
    public void delete(String userid) {
        userDao.delete(userid);
    }

    /**
     * ユーザーID検索
     * @return 検索結果
     */
    public User findById(String userid) {
        return userDao.findById(userid);
    }

    /**
     * ユーザ情報更新
     * @param userUpdateRequest リクエストデータ
     */
    public void update(UserUpdateRequest userUpdateRequest) {
        userDao.update(userUpdateRequest);
    }
}
