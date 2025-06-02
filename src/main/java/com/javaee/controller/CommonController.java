package com.javaee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.javaee.po.User;
import com.javaee.service.UserService;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private UserService userService;

    // 跳转到修改密码页面
    @GetMapping("/modifypwd")
    public String showModifyPwdPage() {
        return "common/modifypwd";
    }

    // 处理修改密码表单提交
    @PostMapping("/modifypwd")
    public String modifyPassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            // 未登录，跳转到登录页
            return "redirect:/user/login";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "新密码和确认密码不一致");
            return "common/modifypwd";
        }

        User user = userService.selectUserByUsername(username);
        if (user == null) {
            model.addAttribute("error", "用户不存在");
            return "common/modifypwd";
        }

        if (!user.getPassword().equals(oldPassword)) {
            model.addAttribute("error", "旧密码错误");
            return "common/modifypwd";
        }

        // 更新密码
        user.setPassword(newPassword);
        userService.updatepassword(user);

        model.addAttribute("success", "密码修改成功");
        return "user/login";
    }

    // 跳转到注销确认页
    @GetMapping("/logout")
    public String showLogoutPage() {
        return "common/logout";
    }

    // 处理注销操作
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}
