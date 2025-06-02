package com.javaee.mapper;

import com.javaee.po.Work;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkMapper {
    List<Work> findAll();
    
    Work findById(@Param("id") Integer id);
    
    void insert(Work work);
    
    void update(Work work);
    
    void deleteById(@Param("id") Integer id);
    
    Work getByActorId(@Param("actorId") Integer actorId);

    List<Work> listAllWorks(Object object);

    List<Work> findAllGenres();
}
