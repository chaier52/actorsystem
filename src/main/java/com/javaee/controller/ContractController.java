package com.javaee.controller;

import com.javaee.po.Actor;
import com.javaee.po.Contract;
import com.javaee.service.ActorService;
import com.javaee.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;

    @Autowired
    private ActorService actorService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // 合同列表
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<Contract> contracts = contractService.listAllContracts(keyword);
        model.addAttribute("contracts", contracts);
        return "contract/list";
    }

    // 跳转到添加/编辑表单
    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        // 获取所有演员列表
        List<Actor> actors = actorService.listAllActor(null);
        model.addAttribute("actors", actors);

        if (id != null) {
            Contract contract = contractService.getById(id);
            model.addAttribute("contract", contract);
        } else {
            model.addAttribute("contract", new Contract());
        }
        return "contract/form";
    }

    // 保存合同
    @PostMapping("/save")
    public String save(@ModelAttribute Contract contract, Model model) {
        try {
            if (contract.getContractId() == null) {
                contractService.addContract(contract);
            } else {
                contractService.updateContract(contract);
            }
            return "redirect:/contract/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "保存失败");
            return "contract/form";
        }
    }

    // 删除合同
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        contractService.deleteContract(id);
        return "redirect:/contract/list";
    }
}