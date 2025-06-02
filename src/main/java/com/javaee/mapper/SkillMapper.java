package com.javaee.mapper;

import com.javaee.po.Skill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SkillMapper {
    List<Skill> findAll();
    
    Skill findById(@Param("id") Integer id);
    
    void insert(Skill skill);
    
    void update(Skill skill);
    
    void deleteById(@Param("id") Integer id);

    List<Skill> listAllSkills(Object object);
    
    Skill findByName(@Param("skillName") String skillName);
}
