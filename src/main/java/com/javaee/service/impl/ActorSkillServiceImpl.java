package com.javaee.service.impl;

import com.javaee.mapper.ActorSkillMapper;
import com.javaee.po.ActorSkill;
import com.javaee.service.ActorSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorSkillServiceImpl implements ActorSkillService {
    @Autowired
    private ActorSkillMapper actorSkillMapper;

    @Override
    public List<ActorSkill> getSkillsByActorId(Integer actorId) {
        return actorSkillMapper.getSkillsByActorId(actorId);
    }

    @Override
    public void insert(Integer actorId, Integer skillId) {
        actorSkillMapper.insert(actorId, skillId);
    }
    
    @Override
    public void delete(Integer actorId, Integer skillId) {
        actorSkillMapper.delete(actorId, skillId);
    }

    @Override
    public void deleteByActorId(Integer actorId) {
        actorSkillMapper.deleteByActorId(actorId);
    }
}

