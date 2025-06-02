package com.javaee.controller;

import com.javaee.mapper.ActorWorkMapper;
import com.javaee.po.Work;
import com.javaee.service.WorkService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/actor_work")
public class ActorWorkController {
    @Autowired
    private ActorWorkMapper actorWorkMapper;

    @Autowired
    private WorkService workService;

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword,
                      HttpSession session, Model model) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }

        List<Work> works = actorWorkMapper.getWorksByActorId(actorId);
        if (keyword != null && !keyword.trim().isEmpty()) {
            works = works.stream()
                    .filter(w -> w.getWorkName().contains(keyword))
                    .toList();
        }
        model.addAttribute("works", works);
        return "actor_work/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id,
                      HttpSession session, Model model) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }

        if (id != null) {
            Work work = workService.getById(id);
            model.addAttribute("work", work);
        }
        
        return "actor_work/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Work work, HttpSession session) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }

        try {
            if (work.getWorkId() == null) {
                // 新增作品
                Integer workId = workService.addWork(work);
                // 建立演员和作品的关联
                actorWorkMapper.insert(actorId, workId);
            } else {
                // 更新作品
                workService.updateWork(work);
            }
            return "redirect:/actor_work/list";
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = URLEncoder.encode("操作失败", StandardCharsets.UTF_8);
            return "redirect:/actor_work/form?error=" + errorMsg;
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer workId, HttpSession session) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }

        try {
            actorWorkMapper.deleteActorWork(actorId, workId);
            return "redirect:/actor_work/list";
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = URLEncoder.encode("删除失败", StandardCharsets.UTF_8);
            return "redirect:/actor_work/list?error=" + errorMsg;
        }
    }
}
