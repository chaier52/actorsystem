package com.javaee.controller;

import com.javaee.mapper.ActorTypeMapper;
import com.javaee.po.ActorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/actor_type")
public class ActorTypeController {

    @Autowired
    private ActorTypeMapper actorTypeMapper;

    @GetMapping("/list")
    public String list(Model model) {
        List<ActorType> types = actorTypeMapper.findAll();
        model.addAttribute("types", types);
        return "actor_type/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            ActorType type = actorTypeMapper.findById(id);
            model.addAttribute("actorType", type);
        } else {
            model.addAttribute("actorType", new ActorType());
        }
        return "actor_type/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ActorType actorType, Model model) {
        try {
            if (actorType.getActor_TypeId() == null) {
                actorTypeMapper.insert(actorType);
            } else {
                actorTypeMapper.update(actorType);
            }
            return "redirect:/actor_type/list";
        } catch (Exception e) {
            model.addAttribute("error", "保存演员类型失败");
            model.addAttribute("actorType", actorType);
            return "actor_type/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        actorTypeMapper.deleteById(id);
        return "redirect:/actor_type/list";
    }
}
