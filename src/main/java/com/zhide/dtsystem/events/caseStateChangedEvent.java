package com.zhide.dtsystem.events;

import org.springframework.context.ApplicationEvent;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: casesMainChangeEvent
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月04日 13:54
 **/
public class caseStateChangedEvent extends ApplicationEvent {
    List<String> users;
    String id;
    String name;
    String type;
    String message;
    Date  times;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public caseStateChangedEvent(Object source) {
        super(source);
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimes() {
        return times;
    }

    public void setTimes(Date times) {
        this.times = times;
    }
}
