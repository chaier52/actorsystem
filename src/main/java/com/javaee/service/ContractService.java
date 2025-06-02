package com.javaee.service;

import com.javaee.po.Contract;

import java.util.List;

public interface ContractService {
    List<Contract> listAllContracts(String keyword);
    Contract getById(Integer id);
    Contract getByActorId(Integer actorId);
    void addContract(Contract contract);
    void updateContract(Contract contract);
    void deleteContract(Integer id);
    void deleteByActorId(Integer actorId);
}
