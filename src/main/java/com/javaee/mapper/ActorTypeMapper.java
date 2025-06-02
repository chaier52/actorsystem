package com.javaee.mapper;

import com.javaee.po.ActorType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActorTypeMapper {
    List<ActorType> findAll();
    
    ActorType findById(@Param("id") Integer id);
    
    void insert(ActorType actorType);
    
    void update(ActorType actorType);
    
    void deleteById(@Param("id") Integer id);
}
