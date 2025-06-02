package com.javaee.mapper;

import com.javaee.po.Actor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActorMapper {
    List<Actor> listAllActor(@Param("keyword") String keyword);
    Actor getById(@Param("id") Integer id);
    void addActor(Actor actor);
    void updateActor(Actor actor);
    void deleteActor(@Param("id") Integer id);
    Actor getByName(@Param("name") String name);
}