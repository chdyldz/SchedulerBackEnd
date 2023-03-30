package com.scheduler.n11.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Schedule {
    private AmEventSchedule am;
    private PmEventSchedule pm;

}
