package com.rohithkankipati.projects.Sked.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohithkankipati.projects.Sked.dto.EventDTO;
import com.rohithkankipati.projects.Sked.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@PostMapping("/add")
	public ResponseEntity<Map<String, Object>> addEvent(@RequestBody @Valid EventDTO eventDTO, @RequestHeader("X-User-ID") Long userId) {
		try {
			
			
			
			Map<String, Object> response = eventService.addEvent(eventDTO, userId);
			return ResponseEntity.created(null).body(response);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping("/edit/{eventId}")
    public ResponseEntity<Map<String, Object>> editEvent(@PathVariable Long eventId, @RequestBody @Valid EventDTO eventDTO, @RequestHeader("X-User-ID") Long userId) {
        
		try {
			Map<String, Object> response = eventService.editEvent(eventDTO, userId, eventId);
	        return ResponseEntity.ok(response);
		} catch(Exception e) {
			throw e;
		}
    }
	
	@DeleteMapping("/delete/{eventId}")
    public ResponseEntity<Map<String, Object>> deleteEvent(@PathVariable Long eventId, @RequestHeader("X-User-ID") Long userId) {
		
		try {
			Map<String, Object> response = eventService.deleteEvent(eventId, userId);
	        return ResponseEntity.ok(response);
		} catch(Exception e) {
			throw e;
		}
        
    }
	
	@DeleteMapping("/delete/{eventId}/{date}")
    public ResponseEntity<Map<String, Object>> deleteEventFromRecurring(@PathVariable Long eventId, @RequestHeader("X-User-ID") Long userId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		
		try {
			Instant instant = date.toInstant();
            ZonedDateTime zoned = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
			Map<String, Object> response = eventService.deleteEventFromRecurring(userId, eventId, zoned);
	        return ResponseEntity.ok(response);
		} catch(Exception e) {
			throw e;
		}
        
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<?> getEventsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestHeader("X-User-ID") Long userId) {
        
    	try {
    		Instant instant = date.toInstant();
            ZonedDateTime zoned = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
            var events = eventService.getEventsbyDate(zoned, userId);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
        	throw e;
//            return ResponseEntity.internalServerError().body(Map.of("message", "Error retrieving events: " + e.getMessage()));
        }
    }

}
