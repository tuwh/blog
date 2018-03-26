package com.uncub.blog.user.controller;

import com.sun.istack.internal.NotNull;
import com.uncub.blog.dto.base.User;
import com.uncub.blog.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserContorller {
    @Autowired
    UserService userServic;

    /**
     * 新增用户
     *
     * @param user
     */
    @RequestMapping("/addUser")
    public void addUser(@NotNull User user) {
        userServic.addUser(user);
    }

    @RequestMapping("/viewUser")
    @ResponseBody
    public User viewUser(@NotNull User user) {
        return userServic.queryUser(user).get(0);
    }

    @RequestMapping("/viewUserList")
    @ResponseBody
    public List<User> viewUserList(@NotNull User user) {
        return userServic.queryUser(user);
    }
}
