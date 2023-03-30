package com.scheduler.n11.demo.model;

import java.util.ArrayList;
import java.util.List;


public class ScheduleList {
    List<Schedule> scheduleList;

    public void add(Schedule schedule){
        scheduleList.add(schedule);
    }

    public List<Schedule> getScheduleList(){
        return new ArrayList<>(scheduleList);
    }
}
