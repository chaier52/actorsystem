package com.javaee.service;

import com.javaee.po.Work;

import java.util.List;

public interface WorkService {
    List<Work> listAllWorks(String keyword);
    Work getById(Integer id);
    Work getByActorId(Integer actorid);
    Integer addWork(Work work);
    void updateWork(Work work);
    void deleteWork(Integer id);
    List<Work> findAllGenres();
}
