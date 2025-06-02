package com.javaee.controller;

import com.javaee.mapper.SkillMapper;
import com.javaee.po.ActorSkill;
import com.javaee.po.Skill;
import com.javaee.service.ActorSkillService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/actor_skill")
public class ActorSkillController {
    @Autowired
    private ActorSkillService actorSkillService;

    @Autowired
    private SkillMapper skillMapper;

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword,
                      HttpSession session, Model model) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }

        List<ActorSkill> skills = actorSkillService.getSkillsByActorId(actorId);
        if (keyword != null && !keyword.trim().isEmpty()) {
            skills = skills.stream()
                    .filter(s -> s.getSkillName().contains(keyword))
                    .toList();
        }
        model.addAttribute("skills", skills);
        return "actor_skill/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id,
                      HttpSession session, Model model) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }

        if (id != null) {
            // 编辑现有技能
            Skill skill = skillMapper.findById(id);
            if (skill != null) {
                model.addAttribute("skill", skill);
            }
        }
        return "actor_skill/form";
    }

    @PostMapping("/save")
    public String save(@RequestParam(required = false) Integer skillId,
                      @RequestParam String skillName,
                      HttpSession session) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }

        try {
            if (skillId != null) {
                // 更新现有技能
                Skill skill = new Skill();
                skill.setSkillId(skillId);
                skill.setSkillName(skillName);
                skillMapper.update(skill);
                return "redirect:/actor_skill/list";
            } else {
                // 添加新技能
                // 先检查技能是否已存在
                Skill existingSkill = skillMapper.findByName(skillName);
                if (existingSkill == null) {
                    // 如果技能不存在，创建新技能
                    Skill newSkill = new Skill();
                    newSkill.setSkillName(skillName);
                    skillMapper.insert(newSkill);
                    skillId = newSkill.getSkillId();
                } else {
                    skillId = existingSkill.getSkillId();
                }
                
                // 建立演员和技能的关联
                actorSkillService.insert(actorId, skillId);
                return "redirect:/actor_skill/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/actor_skill/form?error=操作失败";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer skillId, HttpSession session) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }

        try {
            actorSkillService.delete(actorId, skillId);
            return "redirect:/actor_skill/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/actor_skill/list?error=删除失败";
        }
    }
}
