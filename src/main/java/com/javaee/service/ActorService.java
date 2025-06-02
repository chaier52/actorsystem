package com.javaee.service;
import com.javaee.po.Actor;
import java.util.List;

public interface ActorService {
    List<Actor> listAllActor(String keyword);
    Actor getById(Integer id);
    void addActor(Actor actor);
    void updateActor(Actor actor);
    void deleteActor(Integer id);
    Actor getByName(String name);
}