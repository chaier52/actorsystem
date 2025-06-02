package com.javaee.service;

import com.javaee.po.Work;

import java.util.List;

public interface ActorWorkService {
    List<Work> getWorksByActorId(Integer actorId);
    void updateActorWork(Integer actorId, List<Integer> workId);
    void insert(Integer actorId, Integer workId);
    void deleteActorWork(Integer actorId, Integer workId);
    void deleteByActorId(Integer actorId);
}
