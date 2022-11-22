package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DataUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hp
 * @date 2022-11-07 12:51
 * @explain
 */

@Controller
public class UserController {

    @Autowired
    UserService userServicer=null;

    private ReturnObject returnObject=new ReturnObject();

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String ToLogin(){

        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody  Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response){

         Map<String,Object> map=new HashMap<>();
         map.put("loginAct",loginAct);
         map.put("loginPwd",loginPwd);
         User user=userServicer.queryByactAdpwd(map);
         String nowStr= DataUtils.formateDateTime(new Date());

        if(user==null){
         //用户名和密码不匹配
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名和密码不匹配");
         }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
         //ip不允许
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("ip受限");
         }else if(user.getCreatetime().compareTo(nowStr)<0){
            //账号已过期
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("账号已过期");
        }else if(user.getLockState().equals(Contants.RETURN_OBJECT_CODE_FAIL)){
            //账号被锁定
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("账号被锁定");
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS );
            returnObject.setMessage("登陆成功");
            request.getSession().setAttribute(Contants.SESSION_USER,user);

            if("true".equals(isRemPwd)){
                Cookie c1=new Cookie("loginAct",user.getLoginAct());
                c1.setMaxAge(10*24*60*60);
                Cookie c2 = new Cookie("loginPwd", user.getLoginPwd());
                c2.setMaxAge(10*24*60*60);
                response.addCookie(c1);
                response.addCookie(c2);
            }else{
                Cookie c1=new Cookie("loginAct",loginAct);
                Cookie c2=new Cookie("loginAct",loginAct);
            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        Cookie c1=new Cookie("loginAct","1");
        Cookie c2=new Cookie("loginPwd","1");
        c1.setMaxAge(0);
        c2.setMaxAge(0);
        response.addCookie(c1);
        response.addCookie(c2);
        request.getSession().invalidate();
        return "redirect:/";
    }
}
