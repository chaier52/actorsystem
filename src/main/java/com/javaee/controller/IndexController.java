package com.javaee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // 处理根路径访问
    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/user/user_list";
        }
        return "redirect:/user/login";
    }

    @GetMapping("/index")
    public String home(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/user/user_list";
        }
        return "redirect:/user/login";
    }

    // 处理page页面
    @GetMapping("/page")
    public String showPage(HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/user/login";
        }
        return "page";
    }
}