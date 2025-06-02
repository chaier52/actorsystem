package com.javaee.controller;

import com.javaee.mapper.SkillMapper;
import com.javaee.po.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillMapper skillMapper;

    @GetMapping("/list")
    public String list(Model model) {
        List<Skill> skills = skillMapper.findAll();
        model.addAttribute("skills", skills);
        return "skill/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Skill skill = skillMapper.findById(id);
            model.addAttribute("skill", skill);
        } else {
            model.addAttribute("skill", new Skill());
        }
        return "skill/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Skill skill, Model model) {
        try {
            if (skill.getSkillId() == null) {
                skillMapper.insert(skill);
            } else {
                skillMapper.update(skill);
            }
            return "redirect:/skill/list";
        } catch (Exception e) {
            model.addAttribute("error", "保存技能失败");
            model.addAttribute("skill", skill);
            return "skill/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        skillMapper.deleteById(id);
        return "redirect:/skill/list";
    }
}