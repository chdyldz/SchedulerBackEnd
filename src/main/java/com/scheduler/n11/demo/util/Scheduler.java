package com.scheduler.n11.demo.util;

import com.scheduler.n11.demo.entity.Event;
import com.scheduler.n11.demo.model.EventDto;
import com.scheduler.n11.demo.model.EventList;
import com.scheduler.n11.demo.model.Schedule;
import com.scheduler.n11.demo.model.ScheduleList;
import com.scheduler.n11.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Scheduler {
    AtomicInteger sum = new AtomicInteger(0);
    AtomicInteger maxSum = new AtomicInteger(0);
    public List<Schedule> ScheduleProgram( List<Event> events ){


        List<List<Event>> subLists = new ArrayList<>();
        events.sort(Comparator.comparing(Event::getDuration).reversed());
        maxSum.set(180);

        while (!events.isEmpty()) {
            List<Event> subList = new ArrayList<>();
            sum.set(0);
            sum.set(events.stream()
                    .takeWhile(e -> {
                        if(sum.get() + e.duration <= maxSum.get()){
                            sum.set(sum.get()+e.duration);
                            return true;
                        }else return false;

                    })
                    .peek(subList::add)
                    .mapToInt(e -> e.duration)
                    .sum());
            events.removeAll(subList);
            if(maxSum.get()==240 && sum.get() >= 180){
                Event event = new Event();
                event.setDuration(maxSum.get()-sum.get());
                event.setName("Networking");
                subList.add(event);
            }else if (sum.get() < maxSum.get() && !events.isEmpty()) {
                Event remove = subList.stream()
                        .filter(e -> e.duration > maxSum.get() - sum.get())
                        .findFirst()
                        .orElse(null);
                if (remove != null) {
                    subList.remove(remove);
                    sum.set(sum.get()-remove.duration);
                    events.add(remove);
                }
                if (!events.isEmpty()) {
                    Event add = events.stream()
                            .filter(e -> sum.get() + e.duration == maxSum.get())
                            .findFirst()
                            .orElse(events.stream().sorted(Comparator.comparing(Event::getDuration).reversed()).filter(eventt->sum.get()+eventt.getDuration()<maxSum.get()).findFirst().orElse(null));
                    if (add != null) {
                        subList.add(add);
                        sum.set(sum.get()-add.duration);
                        events.remove(add);
                    }
                }
            }
            subLists.add(subList);
            if (subLists.size()%2!=0 && !events.isEmpty()) {
                maxSum.set(240);
            }else maxSum.set(180);
        }
        return null;
    }

    private Boolean populateAmEvents(Schedule schedule, Event event) {

            Boolean amEventAddResult = schedule.getAm().addEvent(event);
            if (!amEventAddResult){
                schedule.getAm().setNetworking();
            }
            return amEventAddResult;
    }

    private Boolean populatePmEvents(Schedule schedule, Event event) {

        Boolean amEventAddResult = schedule.getPm().addEvent(event);
        if (!amEventAddResult){
            schedule.getPm().setNetworking();
        }
        return amEventAddResult;
    }

    private static int compare(Integer a, Integer b) {
        return a < b ? -1
                : a > b ? 1
                : 0;
    }


}
