package com.javaee.mapper;

import com.javaee.po.ContactInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContactInfoMapper {
    List<ContactInfo> findAll();
    
    ContactInfo findById(@Param("id") Integer id);
    
    void insert(ContactInfo contactInfo);
    
    void update(ContactInfo contactInfo);
    
    void deleteById(@Param("id") Integer id);

    ContactInfo getByActorId(@Param("actorId") Integer actorId);

    void deleteByActorId(@Param("actorId") Integer actorId);

} 