package com.kagami.rxsample.util;

/**
 * Created by sinceredeveloper on 16/12/23.
 */

public class FakerTask {
    private String text;
    private long cost;
    public FakerTask(String in,long cost){
        text=in;
        this.cost=cost;

    }

    public String start(){
        try {
            Thread.sleep(cost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return text;
    }

}
