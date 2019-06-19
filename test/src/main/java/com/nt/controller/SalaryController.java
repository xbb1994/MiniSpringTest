package com.nt.controller;

import com.nt.beans.AutoWired;
import com.nt.service.SalaryService;
import com.nt.web.mvc.Controller;
import com.nt.web.mvc.RequestMapping;
import com.nt.web.mvc.RequestParam;

/**
 * @author ：xubb
 * @date ：Created in 2019/6/14 22:09
 * @description：
 */
@Controller
public class SalaryController {


    @AutoWired
    private SalaryService salaryService;

//    http://localhost:6699/getSalary?name=1&experience=2
    @RequestMapping("/getSalary")
    public int getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {
        return salaryService.getSalary(Integer.parseInt(experience));
    }
}
