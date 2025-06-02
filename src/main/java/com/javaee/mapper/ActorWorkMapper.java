package com.javaee.mapper;

import com.javaee.po.Work;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ActorWorkMapper {
    List<Work> getWorksByActorId(@Param("actorId") Integer actorId);
    void insert(@Param("actorId") Integer actorId, @Param("workId") Integer workId);
    void deleteActorWork(@Param("actorId") Integer actorId, @Param("workId") Integer workId);
    void deleteByActorId(@Param("actorId") Integer actorId);
    void updateWork(@Param("actorId") Integer actorId, @Param("workIds") List<Integer> workIds);
}
