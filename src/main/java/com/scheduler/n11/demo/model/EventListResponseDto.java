package com.scheduler.n11.demo.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventListResponseDto {
    private String eventTime;
    private String eventName;
    private String duration;
}
