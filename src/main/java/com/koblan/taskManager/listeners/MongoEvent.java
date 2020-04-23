package com.koblan.taskManager.listeners;

import org.springframework.context.ApplicationEvent;

public class MongoEvent extends ApplicationEvent {
    private String message;
 
    public MongoEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
