package com.bjpowernode.crm.workbench.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DataUtils;
import com.bjpowernode.crm.commons.utils.uuidUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Activitiy;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author hp
 * @date 2022-11-20 13:37
 * @explain
 */

@Controller
public class ActivityRemarkController {

    @Autowired
    ActivityService activityService;

    @Autowired
    ActivityRemarkService activityRemarkService=null;
    private int res;

     ReturnObject returnObject=new ReturnObject();
    @RequestMapping("/workbench/activity/queryActivityForDetail.do")
    public String queryActivityForDetail(String id, HttpServletRequest request){
        Activitiy activitiy = activityService.queryActivityForDetail(id);
        List<ActivityRemark> activityRemarkList =null;
        activityRemarkList=activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        request.setAttribute("activity",activitiy);
        request.setAttribute("activityRemarkList",activityRemarkList);
        return "workbench/activity/detail";
    }

    @RequestMapping("/workbench/activity/saveActivityRemark.do")
    public @ResponseBody Object saveActivityRemark(ActivityRemark activityRemark,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute(Contants.SESSION_USER);
        activityRemark.setCreateBy(user.getId());
        String date=DataUtils.formateDate(new Date());
        activityRemark.setCreateTime(date);
        activityRemark.setId(uuidUtils.uuid);
        activityRemark.setEditFlag(Contants.Edit_Remark_NO_EDITED);
        try {
            int res= activityRemarkService.saveActivityRemark(activityRemark);
            if(res==0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后...");
            }else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetObject(activityRemark);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    public @ResponseBody Object deleteActivityRemarkById(String id){
        try {
            int res=activityRemarkService.deleteActivityRemarkById(id);
            if(res==0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后...");
            }else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后...");
        }
          return returnObject;
    }
}
