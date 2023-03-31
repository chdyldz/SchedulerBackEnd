package com.scheduler.n11.demo.controller;

import com.scheduler.n11.demo.entity.Event;
import com.scheduler.n11.demo.model.EventDto;
import com.scheduler.n11.demo.model.Schedule;
import com.scheduler.n11.demo.service.EventService;
import com.scheduler.n11.demo.util.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private Scheduler scheduler;

    AtomicInteger atomicInteger=new AtomicInteger(0);

    @RequestMapping(value = { "/save", "/save/" }, method = RequestMethod.POST)
    public Object save(@RequestBody EventDto event) {
        try {
            if(event.getDuration()==null||event.getDuration().equals(0))
                return new ResponseEntity<>("duration cant be null or zero",HttpStatus.BAD_REQUEST);
            eventService.save(event);
            return new ResponseEntity<>("1",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value={"/get","/get/"},method=RequestMethod.GET)
    public Object getEventList(){
        try {
            List<Event> allEvents = eventService.getAllEvents();
            return new ResponseEntity<>(allEvents,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @RequestMapping(value={"/getSchedule","/getSchedule/"},method=RequestMethod.GET)
    public Object getSchedule(){
        try {
            return new ResponseEntity<>(scheduler.getEventList(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
