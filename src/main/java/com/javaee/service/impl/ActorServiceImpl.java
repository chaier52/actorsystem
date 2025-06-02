package com.javaee.service.impl;
import com.javaee.mapper.ActorMapper;
import com.javaee.po.Actor;
import com.javaee.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {
    @Autowired
    private ActorMapper actorMapper;

    @Override
    public List<Actor> listAllActor(String keyword) {
        return actorMapper.listAllActor(keyword);
    }

    @Override
    public Actor getById(Integer id) {
        return actorMapper.getById(id);
    }

    @Override
    public void addActor(Actor actor) {
        actorMapper.addActor(actor);
    }

    @Override
    public void updateActor(Actor actor) {
        actorMapper.updateActor(actor);
    }

    @Override
    public void deleteActor(Integer id) {
        actorMapper.deleteActor(id);
    }

    @Override
    public Actor getByName(String name) {
        return actorMapper.getByName(name);
    }
}