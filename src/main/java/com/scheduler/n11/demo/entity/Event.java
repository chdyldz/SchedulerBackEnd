package com.scheduler.n11.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Setter
@Table(name = "event")
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "duration", nullable = false)
    public Integer duration;

    @Column(name = "eventTime", nullable = false)
    public String eventTime;

    @Override
    public String toString(){
        return this.eventTime+" "+this.name+" "+this.duration+ " min";
    }
}

