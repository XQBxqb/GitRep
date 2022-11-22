package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DataUtils;
import com.bjpowernode.crm.commons.utils.uuidUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activitiy;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CheckedOutputStream;

/**
 * @author hp
 * @date 2022-11-10 15:59
 * @explain
 */
@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    ReturnObject returnObject=new ReturnObject();

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> userList= userService.queryAllUsers();
        request.setAttribute(Contants.UserList,userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreatActivity.do")
    public @ResponseBody Object saveCreatActivity(Activitiy activitiy,HttpServletRequest request){

         activitiy.setId(uuidUtils.uuid);
         User user=(User)request.getSession().getAttribute(Contants.SESSION_USER);
         activitiy.setCreateBy(user.getId());
         activitiy.setCreateTime(DataUtils.formateDate(new Date()));

        try {
            int res=activityService.savecreatActivity(activitiy);
            if(res==1){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityByCondition.do")
    public @ResponseBody Object queryActivityByCondition(String name,String owner,String startDate,String endDate,Integer pageSize,Integer pageNo){
        Map<String,Object> map= new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageSize",pageSize);
        map.put("beginNo",(pageNo-1)*pageSize);
        List<Activitiy> activitiyList=activityService.queryActivityByConditionForPage(map);
        Integer totalRows=activityService.queryActivityByCondition(map);
        Map<String,Object> mapjson=new HashMap<>();
        mapjson.put("activitiyList",activitiyList);
        mapjson.put("totalRows",totalRows);
        return mapjson;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    public @ResponseBody Object deleteActivityByIds(String[] id){
        try {
            int res=activityService.deleteActivityByIds(id);
            if(res!=0){
                returnObject.setCode("1");
            }else{
                returnObject.setCode("0");
                returnObject.setMessage("系统繁忙，请稍后...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode("0");
            returnObject.setMessage("系统繁忙，请稍后...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    public @ResponseBody  Object queryActivityById(String id){
        Activitiy activitiy= activityService.queryActivityById(id);
        return activitiy;
    }

    @RequestMapping("/workbench/activity/updateActivity.do")
    public @ResponseBody Object updateActivity(Activitiy activitiy,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute(Contants.SESSION_USER);
        activitiy.setEditBy(user.getName());
        String date=DataUtils.formateDate(new Date());
        activitiy.setEditTime(date);
        try {
           int res= activityService.updateActivity(activitiy);
            if(res==0){
                returnObject.setCode("0");
                returnObject.setMessage("系统繁忙，请稍后...");
            }else{
                returnObject.setCode("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode("0");
            returnObject.setMessage("系统繁忙，请稍后...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/downActivity.do")
    public void downActivity(HttpServletResponse response) throws IOException {
        List<Activitiy> activitiyList=activityService.queryAllActivity();
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建者");
        cell=row.createCell(9);
        cell.setCellValue("修改时间");
        cell=row.createCell(10);
        cell.setCellValue("修改者");

        for(int i=1;i<=activitiyList.size();i++){
            Activitiy activitiy=activitiyList.get(i-1);
            HSSFRow rows = sheet.createRow(i);
            cell.setCellValue(activitiy.getId());
            cell=rows.createCell(1);
            cell.setCellValue(activitiy.getOwner());
            cell=rows.createCell(2);
            cell.setCellValue(activitiy.getName());
            cell=rows.createCell(3);
            cell.setCellValue(activitiy.getStartDate());
            cell=rows.createCell(4);
            cell.setCellValue(activitiy.getEndDate());
            cell=rows.createCell(5);
            cell.setCellValue(activitiy.getCost());
            cell=rows.createCell(6);
            cell.setCellValue(activitiy.getDescription());
            cell=rows.createCell(7);
            cell.setCellValue(activitiy.getCreateTime());
            cell=rows.createCell(8);
            cell.setCellValue(activitiy.getCreateBy());
            cell=rows.createCell(9);
            cell.setCellValue(activitiy.getEditTime());
            cell=rows.createCell(10);
            cell.setCellValue(activitiy.getEditBy());
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        ServletOutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        wb.close();
        outputStream.close();
        return;
    }
}
