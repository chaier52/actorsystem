package com.javaee.service.impl;

import com.javaee.mapper.ActorAwardMapper;
import com.javaee.po.Award;
import com.javaee.service.ActorAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorAwardServiceImpl implements ActorAwardService {
    @Autowired
    private ActorAwardMapper actorAwardMapper;

    @Override
    public List<Award> getAwardsByActorId(Integer actorId) {
        return actorAwardMapper.getAwardsByActorId(actorId);
    }

    @Override
    public void deleteActorAwards(Integer actorId) {
        actorAwardMapper.deleteActorAwards(actorId);
    }

    @Override
    public void insertActorAwards(Integer actorId, List<Integer> awardIds) {
        actorAwardMapper.insertActorAwards(actorId, awardIds);
    }

    @Override
    public void insert(Integer actorId, Integer awardId) {
        actorAwardMapper.insert(actorId, awardId);
    }

    @Override
    public void deleteByAwardId(Integer awardId) {
        actorAwardMapper.deleteByAwardId(awardId);
    }
    
    @Override
    public void deleteByActorId(Integer actorId) {
        actorAwardMapper.deleteByActorId(actorId);
    }
}
