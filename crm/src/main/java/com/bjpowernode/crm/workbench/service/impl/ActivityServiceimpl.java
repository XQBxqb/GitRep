package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.Activitiy;
import com.bjpowernode.crm.workbench.mapper.ActivitiyMapper;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hp
 * @date 2022-11-10 17:33
 * @explain
 */

@Service("activityService")
public class ActivityServiceimpl implements ActivityService {

    @Autowired
    ActivitiyMapper activitiyMapper;



    @Override
    public int savecreatActivity(Activitiy activitiy) {
        return activitiyMapper.insertActivity(activitiy);
    }

    @Override
    public List<Activitiy> queryActivityByConditionForPage(Map<String, Object> map) {
        return activitiyMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryActivityByCondition(Map<String, Object> map) {
       return activitiyMapper.queryCountByCondition(map);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
       int res=activitiyMapper.deleteActivityById(ids);
       int a=1;
       return res;
    }

    @Override
    public Activitiy queryActivityById(String id) {
        return activitiyMapper.selectActivityById(id);
    }

    @Override
    public int updateActivity(Activitiy activitiy) {
        return activitiyMapper.updateActivity(activitiy);
    }

    @Override
    public List<Activitiy> queryAllActivity() {
        return activitiyMapper.selectAllActivity();
    }

    @Override
    public Activitiy queryActivityForDetail(String id) {
        Activitiy activity=null;
        activity= activitiyMapper.selectActivityByIdForDetail(id);
        return activity;
    }


}
