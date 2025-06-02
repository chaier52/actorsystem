package com.javaee.controller;

import com.javaee.mapper.ActorAwardMapper;
import com.javaee.mapper.ActorMapper;
import com.javaee.mapper.AwardMapper;
import com.javaee.po.Actor;
import com.javaee.po.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/actor_award")
public class ActorAwardController {
    @Autowired
    private ActorAwardMapper actorAwardMapper;

    @Autowired
    private ActorMapper actorMapper;

    @Autowired
    private AwardMapper awardMapper;

    // 演员获奖列表
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<Award> awards = awardMapper.listAllAwards(keyword);
        model.addAttribute("awards", awards);
        return "actor_award/list";
    }

    // 跳转到添加/编辑表单
    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        // 获取所有演员列表
        List<Actor> actors = actorMapper.listAllActor(null);
        model.addAttribute("actors", actors);

        if (id != null) {
            Award award = awardMapper.getById(id);
            model.addAttribute("award", award);
        } else {
            model.addAttribute("award", new Award());
        }
        return "actor_award/form";
    }

    // 保存演员获奖信息
    @PostMapping("/save")
    @Transactional
    public String save(@ModelAttribute Award award, @RequestParam Integer actorId) {
        try {
            // 验证年份范围
            if (award.getAwardYear() < 1901 || award.getAwardYear() > 2155) {
                return "redirect:/actor_award/form?error=" + URLEncoder.encode("year-invalid", StandardCharsets.UTF_8);
            }

            if (award.getAwardId() == null) {
                // 新增奖项
                try {
                    awardMapper.insert(award);
                    actorAwardMapper.insert(actorId, award.getAwardId());
                } catch (Exception e) {
                    e.printStackTrace();
                    return "redirect:/actor_award/form?error=" + URLEncoder.encode("create-failed", StandardCharsets.UTF_8);
                }
            } else {
                // 更新奖项
                try {
                    awardMapper.update(award);
                    actorAwardMapper.deleteActorAwards(actorId);
                    actorAwardMapper.insertActorAwards(actorId, List.of(award.getAwardId()));
                } catch (Exception e) {
                    e.printStackTrace();
                    return "redirect:/actor_award/form?id=" + award.getAwardId() + "&error=" + URLEncoder.encode("update-failed", StandardCharsets.UTF_8);
                }
            }
            return "redirect:/actor_award/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/actor_award/form" + (award.getAwardId() != null ? "?id=" + award.getAwardId() : "") + 
                   "?error=" + URLEncoder.encode("system-error", StandardCharsets.UTF_8);
        }
    }

    // 删除演员获奖信息
    @PostMapping("/delete/{id}")
    @Transactional
    public String delete(@PathVariable("id") Integer id) {
        try {
            // 先删除关联表中的记录
            actorAwardMapper.deleteByAwardId(id);
            // 再删除奖项
            awardMapper.delete(id);
            return "redirect:/actor_award/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/actor_award/list?error=" + URLEncoder.encode("delete-failed", StandardCharsets.UTF_8);
        }
    }
} 