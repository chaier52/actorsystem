package com.javaee.controller;

import com.javaee.mapper.ContactInfoMapper;
import com.javaee.po.ContactInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contact_info")
public class ContactInfoController {

    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @GetMapping("/list")
    public String list(Model model) {
        List<ContactInfo> contactInfos = contactInfoMapper.findAll();
        model.addAttribute("contacts", contactInfos);
        return "contact_info/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            ContactInfo contact = contactInfoMapper.findById(id);
            model.addAttribute("contact", contact);
        } else {
            model.addAttribute("contact", new ContactInfo());
        }
        return "contact_info/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ContactInfo contact, Model model) {
        try {
            if (contact.getContactId() == null) {
                contactInfoMapper.insert(contact);
            } else {
                contactInfoMapper.update(contact);
            }
            return "redirect:/contact_info/list";
        } catch (Exception e) {
            model.addAttribute("error", "保存联系方式失败");
            model.addAttribute("contact", contact);
            return "contact_info/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        contactInfoMapper.deleteById(id);
        return "redirect:/contact_info/list";
    }
} 