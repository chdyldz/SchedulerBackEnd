package com.scheduler.n11.demo.repository;


import com.scheduler.n11.demo.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event,Integer> {

    List<Event> findAll();

}
