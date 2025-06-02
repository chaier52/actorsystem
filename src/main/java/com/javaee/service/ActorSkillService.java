package com.javaee.service;

import com.javaee.po.ActorSkill;
import java.util.List;

public interface ActorSkillService {
    List<ActorSkill> getSkillsByActorId(Integer actorId);
    void insert(Integer actorId, Integer skillId);
    void delete(Integer actorId, Integer skillId);
    void deleteByActorId(Integer actorId);
}
