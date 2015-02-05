package com.haogrgr.springmvc.controller;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = "/cash/in")
    public ModelAndView index(HttpServletResponse response) {
        return new ModelAndView("home");
    }
    
    @ResponseBody
    @RequestMapping(value = "/cash/out/callback")
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        try(ServletInputStream reader = request.getInputStream()){
            String content = IOUtils.toString(reader, "GBK");
            System.out.println("请求正文:" + content);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "test";
    }
    
    
}
