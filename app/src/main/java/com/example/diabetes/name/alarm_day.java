package com.example.diabetes.name;

/**
 * Created by behnam_b on 12/18/2015.
 */
public class alarm_day {

    private String clock;
    private int when_d;
    private int check_10min;
    private int check_2hour;


    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public int getWhen_d() {
        return when_d;
    }

    public void setWhen_d(int when_d) {
        this.when_d = when_d;
    }

    public int getCheck_10min() {
        return check_10min;
    }

    public void setCheck_10min(int check_10min) {
        this.check_10min = check_10min;
    }

    public int getCheck_2hour() {
        return check_2hour;
    }

    public void setCheck_2hour(int check_2hour) {
        this.check_2hour = check_2hour;
    }
}
