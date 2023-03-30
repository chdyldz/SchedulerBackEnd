package com.scheduler.n11.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.scheduler.n11.demo.entity.Event;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class EventDto {

    private String name;
    private Integer duration;

    public EventDto(Event event){
        this.name=event.getName();
        this.duration=event.getDuration();

    }

}
