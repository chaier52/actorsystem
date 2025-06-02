package com.javaee.service.impl;

import com.javaee.mapper.ActorWorkMapper;
import com.javaee.po.Work;
import com.javaee.service.ActorWorkService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ActorWorkServiceImpl implements ActorWorkService {
    @Autowired
    private ActorWorkMapper actorWorkMapper;

    @Override
    public List<Work> getWorksByActorId(Integer actorId) {
        return actorWorkMapper.getWorksByActorId(actorId);
    }

    @Override
    public void updateActorWork(Integer actorId, List<Integer> workIds) {
        actorWorkMapper.updateWork(actorId, workIds);
    }

    @Override
    public void insert(Integer actorId, Integer workId) {
        actorWorkMapper.insert(actorId, workId);
    }
    
    @Override
    public void deleteActorWork(Integer actorId, Integer workId) {
        actorWorkMapper.deleteActorWork(actorId, workId);
    }

    @Override
    public void deleteByActorId(Integer actorId) {
        actorWorkMapper.deleteByActorId(actorId);
    }

}
