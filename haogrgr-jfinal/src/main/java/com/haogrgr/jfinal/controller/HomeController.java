package com.haogrgr.jfinal.controller;

import com.jfinal.core.Controller;

public class HomeController extends Controller {
    
	public void index() throws Exception{
	    renderText("Hello JFinal World.");
	}
	
	public void home() throws Exception{
        renderText("Hello JFinal World Home.");
    }
	
	public void jsonp() throws Exception{
	    String name = getPara("callback");
	    renderJavascript(name + "('xxxx')");
	}
}
