package com.haogrgr.buka.event;

import org.springframework.context.ApplicationEvent;

/**
 * spring context初始化完成事件
 */
public class AppStartedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public AppStartedEvent(Object source) {
        super(source);
    }
}
