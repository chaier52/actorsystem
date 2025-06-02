package com.javaee.mapper;

import com.javaee.po.ActorSkill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActorSkillMapper {
    List<ActorSkill> getSkillsByActorId(@Param("actorId") Integer actorId);
    void insert(@Param("actorId") Integer actorId, @Param("skillId") Integer skillId);
    void delete(@Param("actorId") Integer actorId, @Param("skillId") Integer skillId);
    void deleteByActorId(@Param("actorId") Integer actorId);
}
