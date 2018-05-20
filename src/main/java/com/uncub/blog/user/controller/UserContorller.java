package com.uncub.blog.user.controller;

import com.uncub.blog.dto.base.User;
import com.uncub.blog.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
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

    @RequestMapping("/viewUser1")
    @RequiresRoles("cust")
    public String viewUser1(@NotNull User user) {
        user.setUserNo("tuwh");
        User user1 = userServic.queryUser(user).get(0);
        BeanUtils.copyProperties(user1, user);
        return "/viewUser2";
    }

    @RequestMapping("/viewUserWithModel")
    public ModelAndView viewUserWithModel(@NotNull User user) {
        User user1 = userServic.queryUser(user).get(0);
        return new ModelAndView("/viewUser2", "user1", user1);
    }

    /*@RequestMapping("/login")
    public String login(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserNo(), user.getPassword());
        subject.login(token);
        return "redirect:/index.html";
    }*/

    @RequestMapping("/login")
    public ModelAndView login(User user, boolean rememberMe){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserNo(), user.getPassword());
        token.setRememberMe(rememberMe);
        subject.login(token);
        return new ModelAndView("redirect:/index.html", "user1", user);
//        return "redirect:/index.html";
    }

    @RequestMapping("/loginWithAjax")
    public User loginWithAjax(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserNo(), user.getPassword());
        subject.login(token);
        return user;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(User user, boolean rememberMe){
        Subject subject = SecurityUtils.getSubject();
        /*UsernamePasswordToken token = new UsernamePasswordToken(user.getUserNo(), user.getPassword());
        token.setRememberMe(rememberMe);*/
        subject.logout();
        return new ModelAndView("redirect:/index.html", "user1", user);
//        return "redirect:/index.html";
    }

}
