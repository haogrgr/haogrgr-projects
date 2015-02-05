package com.haogrgr.jfinal.config;

import com.haogrgr.jfinal.controller.HomeController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;

public class MyJFinalConfig extends JFinalConfig{

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/jfinal", HomeController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        
    }

    @Override
    public void configInterceptor(Interceptors me) {
        
    }

    @Override
    public void configHandler(Handlers me) {
        
    }
    
}
