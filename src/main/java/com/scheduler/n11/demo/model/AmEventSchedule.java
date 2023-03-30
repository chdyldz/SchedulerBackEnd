package com.scheduler.n11.demo.model;

import com.scheduler.n11.demo.service.EventSchedule;

import java.util.List;
import com.scheduler.n11.demo.entity.Event;

public class AmEventSchedule implements EventSchedule {

    private List<Event> am;

    @Override
    public Boolean addEvent(Event event) {
        int sum = getSum(am);
        if(sum+ event.getDuration() < 180){
            am.add(event);
            return true;
        }else return false;
    }

    @Override
    public List<Event> setNetworking() {
        int sum = getSum(am);
        return am;
    }

    private int getSum(List<Event> eventList) {
        int sum = eventList.stream().mapToInt(Event::getDuration).sum();
        return sum;
    }
}
