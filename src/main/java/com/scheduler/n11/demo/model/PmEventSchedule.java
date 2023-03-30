package com.scheduler.n11.demo.model;

import com.scheduler.n11.demo.service.EventSchedule;
import com.scheduler.n11.demo.entity.Event;

import java.util.List;

public class PmEventSchedule implements EventSchedule {

    private List<Event> pm;

    @Override
    public Boolean addEvent(Event event) {
        int sum = getSum(pm);
        if(sum+ event.getDuration() < 240){
            pm.add(event);
            return true;
        }else return false;
    }

    @Override
    public List<Event> setNetworking() {
        int sum = getSum(pm);
        if(sum<240){
            Event event = new Event();
            event.setDuration(240-sum);
            event.setId(1);
            event.setName("Networking");
            pm.add(event);
        }
        return pm;
    }

    private int getSum(List<Event> eventList) {
        int sum = eventList.stream().mapToInt(Event::getDuration).sum();
        return sum;
    }
}
