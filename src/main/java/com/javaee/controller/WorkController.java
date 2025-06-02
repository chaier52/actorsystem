package com.javaee.controller;

import com.javaee.mapper.WorkMapper;
import com.javaee.po.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkMapper workMapper;

    @GetMapping("/list")
    public String list(Model model) {
        List<Work> works = workMapper.findAll();
        model.addAttribute("works", works);
        return "work/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        model.addAttribute("genres", workMapper.findAllGenres());
        if (id != null) {
            Work work = workMapper.findById(id);
            model.addAttribute("work", work);
        } else {
            model.addAttribute("work", new Work());
        }
        return "work/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Work work, Model model) {
        try {
            if (work.getWorkId() == null) {
                workMapper.insert(work);
            } else {
                workMapper.update(work);
            }
            return "redirect:/work/list";
        } catch (Exception e) {
            model.addAttribute("error", "保存作品失败");
            model.addAttribute("genres", workMapper.findAllGenres());
            model.addAttribute("work", work);
            return "work/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        workMapper.deleteById(id);
        return "redirect:/work/list";
    }
}
