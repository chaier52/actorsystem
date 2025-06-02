package com.javaee.mapper;

import com.javaee.po.Award;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AwardMapper {
    List<Award> listAllAwards(@Param("keyword") String keyword);
    
    Award getById(Integer id);
    
    void insert(Award award);
    
    void update(Award award);
    
    void delete(Integer id);
}
