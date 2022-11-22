package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hp
 * @date 2022-11-07 15:18
 * @explain
 */

@Controller
public class WorkBenchIndexController {
    @RequestMapping("/workbench/index.do")
    public String index(){
        System.out.println("1");
        return "workbench/index";
    }

}
