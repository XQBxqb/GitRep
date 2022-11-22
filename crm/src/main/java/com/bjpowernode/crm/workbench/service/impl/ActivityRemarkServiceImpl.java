package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hp
 * @date 2022-11-20 13:39
 * @explain
 */

@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId) {
      return activityRemarkMapper.selectRemarkForDetail(activityId);
    }

    @Override
    public int saveActivityRemark(ActivityRemark activityRemarK) {
       return activityRemarkMapper.insertActivityRemark(activityRemarK);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
       return activityRemarkMapper.deleteActivityRemarkById(id);
    }


}
