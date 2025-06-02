package com.javaee.controller;

import com.javaee.mapper.UserMapper;
import com.javaee.po.Actor;
import com.javaee.po.User;
import com.javaee.service.ActorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ActorService actorService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       @RequestParam String role,
                       HttpSession session,
                       Model model) {
        // 检查用户名是否为空
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("error", "用户名不能为空");
            return "user/login";
        }

        // 检查密码是否为空
        if (password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "密码不能为空");
            return "user/login";
        }

        // 检查角色是否为空
        if (role == null || role.trim().isEmpty()) {
            model.addAttribute("error", "请选择登录身份");
            return "user/login";
        }

        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            model.addAttribute("error", "用户不存在");
            return "user/login";
        }

        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "密码错误");
            return "user/login";
        }

        if (!user.getRole().equals(role)) {
            model.addAttribute("error", "所选身份与注册身份不符");
            return "user/login";
        }

        // 登录成功，设置session
        session.setAttribute("username", user.getUsername());
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("role", user.getRole());

        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/user/user_list";
        } else if ("ACTOR".equals(user.getRole())) {
            Actor actor = actorService.getByName(username);
            if (actor != null) {
                session.setAttribute("actorId", actor.getActorId());
                return "redirect:/actor/actor_info";
            } else {
                model.addAttribute("error", "未找到对应的演员信息");
                return "user/login";
            }
        }

        model.addAttribute("error", "登录失败");
        return "user/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String role,
                          Model model) {
        // 检查用户名是否已存在
        User existingUser = userMapper.selectUserByUsername(username);
        if (existingUser != null) {
            model.addAttribute("error", "用户名已存在");
            return "user/register";
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        
        try {
            userMapper.insertUser(user);
            if ("ACTOR".equals(role)) {
                Actor actor = new Actor();
                actor.setName(username);
                actorService.addActor(actor); 
            }
            model.addAttribute("success", "注册成功，请登录");
            return "user/login";
        } catch (Exception e) {
            model.addAttribute("error", "注册失败");
            return "user/register";
        }
    }

    @GetMapping("/user_list")
    public String listUsers(Model model) {
        try {
            List<User> users = userMapper.listAllUsers();
            model.addAttribute("users", users);
            return "user/user_list";
        } catch (Exception e) {
            model.addAttribute("error", "获取用户列表失败");
            return "exception/error";
        }
    }

    @GetMapping("/edit/{id}")
    public String editUserPage(@PathVariable Integer id, Model model) {
        try {
            User user = userMapper.getUserById(id);
            if (user != null) {
                model.addAttribute("user", user);
                return "user/user_form";
            }
            return "redirect:/user/user_list";
        } catch (Exception e) {
            model.addAttribute("error", "获取用户信息失败");
            return "exception/error";
        }
    }

    @PostMapping("/edit")
    public String editUser(User user, Model model) {
        try {
            if (user.getUserId() == null) {
                userMapper.insertUser(user);
            } else {
                userMapper.updateUser(user); 
            }
        return "redirect:/user/user_list";
    } catch (Exception e) {
            model.addAttribute("error", "保存用户失败");
            return "user/user_form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        try {
            userMapper.deleteUser(id);
        } catch (Exception e) {
        }
        return "redirect:/user/user_list";
    }
}
