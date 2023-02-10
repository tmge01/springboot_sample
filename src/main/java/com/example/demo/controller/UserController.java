package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.service.UserService;
import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;

/**
 * ユーザー Controller
 */
@Controller
public class UserController {
    /**
     * UserServiceを使用できるようにする
     */
    @Autowired
    private UserService userService;
    /**
     * ユーザーリストをuserlist.htmlに渡す。
     * 
     * @param model Model
     * @return ユーザー一覧画面
     */
    @GetMapping(value="/")
    public String viewList(Model model){
        List<User> userList = userService.findAll();
        model.addAttribute("userlist", userList);
        return "user/userlist";
    }
    /**
     * ユーザー新規登録画面に遷移
     * @param model Model
     * @return ユーザー新規登録画面
     */
    @GetMapping(value = "/user/add")
    public String displayAdd(Model model) {
        model.addAttribute("userAddRequest", new UserAddRequest());
        return "user/useradd";
    }
    /**
     * ユーザー新規登録
     * @param userRequest リクエストデータ
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String create(@Validated @ModelAttribute UserAddRequest userRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Validationエラーの場合はメッセージを表示
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/useradd";
        }
        // 登録したらユーザー一覧を表示
        userService.save(userRequest);
        return "redirect:/";
    }
    /**
     * ユーザー削除（物理削除）
     * @param userid ユーザーID
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @GetMapping("/user/{userid}/delete")
    public String delete(@PathVariable String userid, Model model) {
        // ユーザー情報の削除
        userService.delete(userid);
        return "redirect:/";
    }
    /**
     * ユーザー編集画面を表示
     * @param userid ユーザーID
     * @param model Model
     * @return ユーザー編集画面
     */
    @GetMapping("/user/{userid}/edit")
    public String viewEdit(@PathVariable String userid, Model model) {
        User user = userService.findById(userid);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserid(user.getUserid());
        userUpdateRequest.setName(user.getName());
        userUpdateRequest.setEmail(user.getEmail());
        model.addAttribute("userUpdateRequest", userUpdateRequest);
        return "user/useredit";
    }
    /**
     * ユーザー更新
     * @param userRequest リクエストデータ
     * @param model Model
     * @return ユーザー編集画面
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute UserUpdateRequest userUpdateRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/useredit";
        }
        // 編集したらユーザー一覧画面に戻る
        userService.update(userUpdateRequest);
        return "redirect:/";
    }   
}

