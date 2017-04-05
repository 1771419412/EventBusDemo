package com.example.eventbusdemo;

/**
 * Created by 雪无痕 on 2017/4/1.
 */

public class Event {
    private String mMsg;
    public Event(String msg){
        mMsg=msg;
    }
    public String getMsg(){
        return mMsg;
    }

}
