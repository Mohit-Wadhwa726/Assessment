package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;

@RestController
public class EventController {

	private static final String TYPE1="pushevent";
	private static final String TYPE2="releaseevent";
	private static final String TYPE3="watchevent";
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
  @Autowired
  private EventRepository eventRepo;

  @PostMapping("/events")
  @ResponseBody
  public ResponseEntity<Event> saveEvent(@RequestBody final Event event){
	  logger.info("Entering saveEvent for type {} repoid {} actorid {}", event.getType(), event.getRepoId(), event.getActorId());
	  final String type=event.getType();
	  if(type.isEmpty()){
		  new ResourceNotFoundException("type cannot be empty");
	  }
	if(type.equalsIgnoreCase(TYPE1) || type.equalsIgnoreCase(TYPE2) || type.equalsIgnoreCase(TYPE3)){
		eventRepo.save(event);
	    Event ev1=eventRepo.findTopByOrderByIdDesc();
	    logger.info("Exiting saveEvent for type {} repoid {} actorid {} id{}", event.getType(), event.getRepoId(), event.getActorId(),event.getId());
	    return  ResponseEntity.ok().body(ev1);
	}
	else{
		new ResourceNotFoundException("type is mismatched");
	}
	return ResponseEntity.badRequest().body(null);
    
  }

  @GetMapping("/repos/{repoid}/events")
  public ResponseEntity<List<Event>> getEventsByRepoId(@PathVariable("repoid") final Integer repoid){
	  logger.info("Entering getEventByRepoId for repoid {}", repoid);
	  List<Event> eventList=new ArrayList<Event>();
	  eventList=eventRepo.findAllByRepoId(repoid);
	  logger.info("Exiting getEventByRepoId for repoid {}", repoid);
	  return ResponseEntity.ok().body(eventList);
  }

  @GetMapping("/users/{actorid}/events")
  public ResponseEntity<List<Event>> getEventsByActorId(@PathVariable("actorid") final Integer actorid){
	logger.info("Entering getEventsByActorId for actorId {} ", actorid);
    List<Event> eventList=new ArrayList<Event>();
    eventList=eventRepo.findAllByActorId(actorid);
    logger.info("Exiting getEventsByActorId for actorId {} ", actorid);
    return ResponseEntity.ok().body(eventList);
  }

  @GetMapping("/events/{id}")
  public ResponseEntity<Event> getEventsById(@PathVariable("id") final Integer id) throws ResourceNotFoundException{ 
	logger.info("Entering getEventsById for id {} ", id);
    Event event=eventRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("No event found for this id="+id));
    logger.info("Exiting getEventsById for id {} ", id);
    return ResponseEntity.ok().body(event);
  }
  
  @GetMapping("/events")
  public List<Event> showAllEvents(){
	  logger.info("Entering showAllEvents for getting list of all events present");
     return eventRepo.findAll();
  }
}