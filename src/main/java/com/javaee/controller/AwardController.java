package com.javaee.controller;

import com.javaee.mapper.ActorMapper;
import com.javaee.mapper.ActorAwardMapper;
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
@RequestMapping("/award")
public class AwardController {
    @Autowired
    private AwardMapper awardMapper;

    @Autowired
    private ActorMapper actorMapper;

    @Autowired
    private ActorAwardMapper actorAwardMapper;

    // 奖项列表
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<Award> awards = awardMapper.listAllAwards(keyword);
        model.addAttribute("awards", awards);
        return "award/list";
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
        return "award/form";
    }

    // 保存奖项
    @PostMapping("/save")
    @Transactional
    public String save(@ModelAttribute Award award, @RequestParam Integer actorId) {
        try {
            if (award.getAwardId() == null) {
                // 新增奖项
                awardMapper.insert(award);
                // 关联演员和奖项
                actorAwardMapper.insert(actorId, award.getAwardId());
            } else {
                // 更新奖项
                awardMapper.update(award);
                // 更新演员和奖项的关联
                actorAwardMapper.deleteActorAwards(actorId);
                actorAwardMapper.insertActorAwards(actorId, List.of(award.getAwardId()));
            }
            return "redirect:/award/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/award/form?error=" + URLEncoder.encode("save-failed", StandardCharsets.UTF_8);
        }
    }

    // 删除奖项
    @PostMapping("/delete/{id}")
    @Transactional
    public String delete(@PathVariable("id") Integer id) {
        try {
            actorAwardMapper.deleteByAwardId(id);
            awardMapper.delete(id);
            return "redirect:/award/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/award/list?error=" + URLEncoder.encode("delete-failed", StandardCharsets.UTF_8);
        }
    }
}