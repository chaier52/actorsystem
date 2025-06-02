package com.javaee.mapper;

import com.javaee.po.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractMapper {
    List<Contract> listAllContracts(@Param("keyword") String keyword);
    Contract getById(@Param("id") Integer id);
    Contract getByActorId(@Param("actorId") Integer actorId);
    void insert(Contract contract);
    void update(Contract contract);
    void delete(@Param("id") Integer id);
    void deleteByActorId(@Param("actorId") Integer actorId);
}
