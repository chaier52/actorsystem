package com.javaee.service;

import com.javaee.po.Award;
import java.util.List;

public interface AwardService {
    List<Award> listAllAwards(String keyword);
    Award getById(Integer id);
    void addAward(Award award);
    void updateAward(Award award);
    void deleteAward(Integer id);
} 