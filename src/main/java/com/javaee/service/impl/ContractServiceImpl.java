package com.javaee.service.impl;

import com.javaee.mapper.ContractMapper;
import com.javaee.po.Contract;
import com.javaee.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractMapper contractMapper;

    @Override
    public List<Contract> listAllContracts(String keyword) {
        return contractMapper.listAllContracts(keyword);
    }

    @Override
    public Contract getById(Integer id) {
        return contractMapper.getById(id);
    }

    @Override
    public Contract getByActorId(Integer actorId) {
        return contractMapper.getByActorId(actorId);
    }

    @Override
    public void addContract(Contract contract) {
        contractMapper.insert(contract);
    }

    @Override
    public void updateContract(Contract contract) {
        contractMapper.update(contract);
    }

    @Override
    public void deleteContract(Integer id) {
        contractMapper.delete(id);
    }

    @Override
    public void deleteByActorId(Integer actorId) {
        contractMapper.deleteByActorId(actorId);
    }
} 