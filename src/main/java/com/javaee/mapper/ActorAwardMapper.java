package com.javaee.mapper;

import com.javaee.po.Award;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActorAwardMapper {
    List<Award> getAwardsByActorId(@Param("actorId") Integer actorId);
    void deleteActorAwards(@Param("actorId") Integer actorId);
    void insertActorAwards(@Param("actorId") Integer actorId, @Param("awardIds") List<Integer> awardIds);
    void insert(@Param("actorId") Integer actorId, @Param("awardId") Integer awardId);
    void deleteByAwardId(@Param("awardId") Integer awardId);
    void deleteByActorId(@Param("actorId") Integer actorId);
}