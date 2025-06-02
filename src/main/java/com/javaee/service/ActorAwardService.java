package com.javaee.service;

import com.javaee.po.Award;

import java.util.List;

public interface ActorAwardService {
    List<Award> getAwardsByActorId(Integer actorId);
    void deleteActorAwards(Integer actorId);
    void insertActorAwards(Integer actorId, List<Integer> awardIds);
    void insert(Integer actorId, Integer awardId);
    void deleteByAwardId(Integer awardId);
    void deleteByActorId(Integer actorId);  
}
