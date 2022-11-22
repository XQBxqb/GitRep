package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activitiy;

import java.util.List;
import java.util.Map;

/**
 * @author hp
 * @date 2022-11-10 17:30
 * @explain
 */
public interface ActivityService {
    int savecreatActivity(Activitiy activitiy);

    List<Activitiy> queryActivityByConditionForPage(Map<String,Object> map);

    int queryActivityByCondition(Map<String,Object> map);

    int deleteActivityByIds(String[] ids);

    Activitiy queryActivityById(String id);

    int updateActivity(Activitiy activitiy);

    List<Activitiy> queryAllActivity();

    Activitiy queryActivityForDetail(String id);
}
