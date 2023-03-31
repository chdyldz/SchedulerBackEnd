package com.scheduler.n11.demo.service;

import com.scheduler.n11.demo.entity.Event;
import com.scheduler.n11.demo.model.EventDto;
import com.scheduler.n11.demo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    public List<Event> getAllEvents(){
        return  eventRepository.findAll();
    }

    public List<Event> scheduleEvents(){
        List<Event> allEvents = eventRepository.findAll();
        return  null;
    }

    public Event save(EventDto eventDto){
        Event event =new Event();
        event.setName(eventDto.getName());
        event.setDuration(eventDto.getDuration());
        return eventRepository.save(event);
    }

    public void removeAll(){
        eventRepository.deleteAll();
    }

    public void saveAll(List<EventDto> events){
        List<Event> eventList=new ArrayList<>();
        for (EventDto eventDto:events){
            Event event =new Event();
            event.setName(eventDto.getName());
            event.setDuration(eventDto.getDuration());
            eventList.add(event);
        }
        eventRepository.saveAll(eventList);
    }
}
