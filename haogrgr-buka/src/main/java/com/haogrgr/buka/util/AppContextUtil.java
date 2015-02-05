package com.haogrgr.buka.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.haogrgr.buka.event.AppStartedEvent;

@Component
public class AppContextUtil implements ApplicationContextAware {

    private static Logger logger = Logger.getLogger(AppContextUtil.class);

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        context.publishEvent(new AppStartedEvent(this));
    }
    
    /**
     * 初始化Spring 使用 classpath*:spring-root.xml
     * @return Spring上下文
     */
    public synchronized static ApplicationContext initSpring(){
        return initSpring("classpath*:spring-root.xml");
    }
    
    /**
     * 初始化Spring
     * @param path Spring配置文件路径 eg:classpath*:spring-root.xml
     * @return Spring上下文
     */
    public synchronized static ApplicationContext initSpring(String path){
        if(context == null){
        	context = new ClassPathXmlApplicationContext(path);
        }
        return context;
    }
    
    /**
     * 通过beanId和class获取spring中注册的bean
     * 
     * @throws Exception ApplicationContext未注入
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        if (context != null) {
            return context.getBean(name, clazz);
        } else {
            logger.error("context 未初始化");
            throw new RuntimeException("context 未初始化");
        }
    }

    /**
     * 通过beanId获取spring中注册的bean
     * 
     * @throws Exception ApplicationContext未注入
     */
    public static Object getBean(String name) {
        if (context != null) {
            return context.getBean(name);
        } else {
            logger.error("context 未初始化");
            throw new RuntimeException("context 未初始化");
        }
    }

    /**
     * 通过class获取spring中注册的bean
     * 
     * @throws Exception ApplicationContext未注入
     */
    public static <T> T getBean(Class<T> clazz) {
        if (context != null) {
            return context.getBean(clazz);
        } else {
            logger.error("context 未初始化");
            throw new RuntimeException("context 未初始化");
        }
    }

    /**
     * 获取spring的上下文对象
     */
    public static ApplicationContext getContext() {
        return context;
    }

    @Cacheable(condition = "", value = { "cacheName" }, key = "#name + #value")
    public String cacheTest(String name, String value) {
        System.err.println("excute method name : " + name + ", " + value);
        return "hello " + name + ", " + value;
    }
    
    public void jobTest1(){
        System.err.println("exec job test1 ===============================");
    }
    
    public void jobTest2(){
        System.err.println("exec job test2 ===============================");
    }
}
