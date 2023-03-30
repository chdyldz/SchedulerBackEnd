package com.scheduler.n11.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EventList {
    private List<EventDto> eventList;

    public void add(EventDto event){
        eventList.add(event);
    }

    public List<EventDto> getEvents(){
        return new ArrayList<>(eventList);
    }

}
