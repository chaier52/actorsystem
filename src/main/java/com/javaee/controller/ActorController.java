package com.javaee.controller;

import com.javaee.mapper.ActorAwardMapper;
import com.javaee.mapper.ActorSkillMapper;
import com.javaee.mapper.ContactInfoMapper;
import com.javaee.mapper.ActorWorkMapper;
import com.javaee.mapper.ActorTypeMapper;
import com.javaee.po.Actor;
import com.javaee.po.ActorSkill;
import com.javaee.po.Award;
import com.javaee.po.ContactInfo;
import com.javaee.po.Contract;
import com.javaee.po.Work;
import com.javaee.po.ActorType;
import com.javaee.service.ActorService;
import com.javaee.service.ContractService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/actor")
public class ActorController {
    @Autowired
    private ActorService actorService;

    @Autowired
    private ActorAwardMapper actorAwardMapper;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ActorTypeMapper actorTypeMapper;

    @Autowired
    private ActorSkillMapper actorSkillMapper;

    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @Autowired
    private ActorWorkMapper actorWorkMapper;

    //演员列表页面
    @GetMapping("/actor_list")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<Actor> actors = actorService.listAllActor(keyword);
        model.addAttribute("actors", actors);
        return "actor/actor_list";
    }

    // 演员详情（管理员视图）
    @GetMapping("/detail/{id}")
    public String actorDetail(@PathVariable("id") Integer id, Model model) {
        // 获取演员基本信息
        Actor actor = actorService.getById(id);
        model.addAttribute("actor", actor);

        // 获取演员类型信息
        if (actor.getTypeId() != null) {
            ActorType actorType = actorTypeMapper.findById(actor.getTypeId());
            model.addAttribute("actorType", actorType);
        }

        // 获取演员技能
        List<ActorSkill> skills = actorSkillMapper.getSkillsByActorId(id);
        model.addAttribute("skill", skills);

        // 获取演员奖项
        List<Award> awards = actorAwardMapper.getAwardsByActorId(id);
        model.addAttribute("award", awards);

        // 获取演员合同
        Contract contract = contractService.getByActorId(id);
        model.addAttribute("contract", contract != null ? List.of(contract) : List.of());

        // 获取演员作品
        List<Work> works = actorWorkMapper.getWorksByActorId(id);
        model.addAttribute("work", works);

        // 获取联系方式
        ContactInfo contact = contactInfoMapper.getByActorId(id);
        model.addAttribute("contact", contact);

        return "actor/detail";
    }

    // 演员个人信息（演员视图）
    @GetMapping("/actor_info")
    public String actorInfo(HttpSession session, Model model) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }
        Actor actor = actorService.getById(actorId);
        model.addAttribute("actor", actor);

        // 获取演员类型信息
        if (actor.getTypeId() != null) {
            ActorType actorType = actorTypeMapper.findById(actor.getTypeId());
            model.addAttribute("actorType", actorType);
        }

        model.addAttribute("skill", actorSkillMapper.getSkillsByActorId(actorId));
        model.addAttribute("award", actorAwardMapper.getAwardsByActorId(actorId));
        Contract contract = contractService.getByActorId(actorId);
        model.addAttribute("contract", contract != null ? List.of(contract) : List.of());
        model.addAttribute("work", actorWorkMapper.getWorksByActorId(actorId));
        ContactInfo contact = contactInfoMapper.getByActorId(actorId);
        model.addAttribute("contact", contact);
        return "actor/actor_info";
    }

    //跳转到编辑表单
    @GetMapping("/form")
    public String form(@RequestParam(required = true) Integer id, Model model) {
        Actor actor = actorService.getById(id);
        if (actor == null) {
            return "redirect:/actor/actor_list";
        }
        model.addAttribute("actor", actor);
        return "actor/form";
    }

    // 保存演员信息
    @PostMapping("/save")
    public String saveActor(@ModelAttribute Actor actor,
                          @RequestParam(value = "skillId", required = false) List<Integer> skillId,
                          @RequestParam(value = "awardId", required = false) List<Integer> awardId,
                          HttpSession session) {
        String role = (String) session.getAttribute("role");
        
        // 确保只能更新已存在的演员
        if (actor.getActorId() == null) {
            return "redirect:/actor/actor_list";
        }
        
        // 更新
        actorService.updateActor(actor);

        // 更新技能
        if (skillId != null) {
            actorSkillMapper.deleteByActorId(actor.getActorId());
            for (Integer sid : skillId) {
                actorSkillMapper.insert(actor.getActorId(), sid);
            }
        }

        // 更新奖项
        if (awardId != null) {
            actorAwardMapper.deleteActorAwards(actor.getActorId());
            actorAwardMapper.insertActorAwards(actor.getActorId(), awardId);
        }

        // 根据角色决定跳转页面
        if ("ACTOR".equals(role)) {
            return "redirect:/actor/actor_info";
        } else {
            return "redirect:/actor/actor_list";
        }
    }

    //删除单个演员
    @DeleteMapping("/delete/{id}")
    public String deleteActor(@PathVariable("id") Integer id) {
        try {
            contractService.deleteByActorId(id);
            actorAwardMapper.deleteByActorId(id);
            contactInfoMapper.deleteByActorId(id);
            actorSkillMapper.deleteByActorId(id);
            actorWorkMapper.deleteByActorId(id);
            actorService.deleteActor(id);
            return "redirect:/actor/actor_list";
        } catch (Exception e) {
            e.printStackTrace(); // 添加错误日志
            return "redirect:/actor/actor_list?error=fail";
        }
    }

    // 演员编辑个人信息（包括联系方式）
    @GetMapping("/detail_form")
    public String detailForm(HttpSession session, Model model) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null) {
            return "redirect:/common/login";
        }
        
        // 获取演员基本信息
        Actor actor = actorService.getById(actorId);
        model.addAttribute("actor", actor);

        // 获取演员类型信息
        if (actor.getTypeId() != null) {
            ActorType actorType = actorTypeMapper.findById(actor.getTypeId());
            model.addAttribute("actorType", actorType);
        }

        // 获取联系方式
        ContactInfo contact = contactInfoMapper.getByActorId(actorId);
        if (contact == null) {
            contact = new ContactInfo();
            contact.setActorId(actorId);
        }
        model.addAttribute("contact", contact);

        return "actor/detail_form";
    }

    // 保存演员详细信息（基本信息+联系方式）
    @PostMapping("/save_detail")
    public String saveDetail(@ModelAttribute Actor actor,
                           @ModelAttribute ContactInfo contact,
                           @RequestParam(required = false) String actor_TypeName,
                           HttpSession session) {
        Integer actorId = (Integer) session.getAttribute("actorId");
        if (actorId == null || !actorId.equals(actor.getActorId())) {
            return "redirect:/common/login";
        }

        try {
            // 处理演员类型
            if (actor_TypeName != null && !actor_TypeName.trim().isEmpty()) {
                // 先查找是否存在该类型
                List<ActorType> types = actorTypeMapper.findAll();

                if (types != null && !types.isEmpty()) {
                    ActorType existingType = types.stream()
                        .filter(t -> t != null && t.getActor_TypeName() != null && t.getActor_TypeName().equals(actor_TypeName.trim()))
                        .findFirst()
                        .orElse(null);

                    if (existingType != null) {
                        // 如果类型存在，使用现有的typeId
                        actor.setTypeId(existingType.getActor_TypeId());
                    } else {
                        // 如果类型不存在，创建新类型
                        ActorType newType = new ActorType();
                        newType.setActor_TypeName(actor_TypeName.trim());
                        actorTypeMapper.insert(newType);
                        actor.setTypeId(newType.getActor_TypeId());
                    }
                }
            }

            // 更新演员基本信息
            actorService.updateActor(actor);

            // 更新或插入联系方式
            ContactInfo existingContact = contactInfoMapper.getByActorId(actorId);
            if (existingContact == null) {
                contact.setActorId(actorId);
                contactInfoMapper.insert(contact);
            } else {
                contact.setContactId(existingContact.getContactId());
                contact.setActorId(actorId);
                contactInfoMapper.update(contact);
            }

            return "redirect:/actor/actor_info";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/actor/detail_form?error=save-failed";
        }
    }
}