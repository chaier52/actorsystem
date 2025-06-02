package com.javaee.service.impl;

import com.javaee.mapper.WorkMapper;
import com.javaee.po.Work;
import com.javaee.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired
    private WorkMapper workMapper;

    @Override
    public List<Work> listAllWorks(String keyword) {
        return workMapper.listAllWorks(keyword);
    }

    @Override
    public Work getById(Integer id) {
        return workMapper.findById(id);
    }

    @Override
    public Integer addWork(Work work) {
        workMapper.insert(work);
        return work.getWorkId();
    }

    @Override
    public void updateWork(Work work) {
        workMapper.update(work);
    }

    @Override
    public void deleteWork(Integer id) {
        workMapper.deleteById(id);
    }

    @Override
    public List<Work> findAllGenres() {
        return workMapper.findAllGenres();
    }

    @Override
    public Work getByActorId(Integer actorid) { return workMapper.getByActorId(actorid); }
}
