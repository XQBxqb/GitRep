package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @author hp
 * @date 2022-11-20 13:39
 * @explain
 */


public interface ActivityRemarkService {
     List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

     int saveActivityRemark(ActivityRemark activityRemarK);

     int deleteActivityRemarkById(String id);
}
