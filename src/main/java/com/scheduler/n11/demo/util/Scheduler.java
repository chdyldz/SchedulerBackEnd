package com.scheduler.n11.demo.util;

import com.scheduler.n11.demo.entity.Event;
import com.scheduler.n11.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Scheduler {

    @Autowired
    private EventService eventService;
    AtomicInteger sum = new AtomicInteger(0);
    AtomicInteger maxSum = new AtomicInteger(0);


    public String getEventList(){
        Calendar c = new GregorianCalendar();
        StringBuilder sb=new StringBuilder();
        List<List<Event>> allEvents = ScheduleProgram(eventService.getAllEvents());
        for(int i=0;i<allEvents.size();i++){
            List<Event> events = allEvents.get(i);
            SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
            if(i%2==0){
                c.set( Calendar.AM_PM, Calendar.AM );
                c.set(Calendar.HOUR,9);
                c.set(Calendar.MINUTE,0);
            }
            else {Event event=new Event();
                event.setDuration(60);
                event.setName("Lunch");
                event.setEventTime("12:00 PM");
                Collections.reverse(events);
                events.add(event);
                Collections.reverse(events);
                c.set( Calendar.AM_PM, Calendar.PM );
                c.set(Calendar.HOUR,1);
                c.set(Calendar.MINUTE,0);

            }

            for (Event event : events) {
                if (!event.getName().equals("Lunch")) {
                    event.setEventTime(df.format(c.getTime()));
                    c.add(Calendar.MINUTE, event.getDuration());
                }
                sb.append(event);
                sb.append("\n");
            }
        }
        return sb.toString();
    }
    public List<List<Event>> ScheduleProgram( List<Event> events ){
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
            }else {
                maxSum.set(180);
            }

        }
        return subLists;
    }


}
