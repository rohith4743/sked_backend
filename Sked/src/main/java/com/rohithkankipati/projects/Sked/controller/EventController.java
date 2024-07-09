package com.rohithkankipati.projects.Sked.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping("/edit")
    public ResponseEntity<Map<String, Object>> editEvent(@RequestBody @Valid EventDTO eventDTO, @RequestHeader("userId") Long userId) {
        
		try {
			Map<String, Object> response = eventService.editEvent(eventDTO, userId);
	        return ResponseEntity.ok(response);
		} catch(Exception e) {
			throw e;
		}
    }
	
	@DeleteMapping("/delete/{eventId}")
    public ResponseEntity<Map<String, Object>> deleteEvent(@PathVariable Long eventId, @RequestHeader("userId") Long userId) {
		
		try {
			Map<String, Object> response = eventService.deleteEvent(eventId, userId);
	        return ResponseEntity.ok(response);
		} catch(Exception e) {
			throw e;
		}
        
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<?> getEventsByDate(@RequestParam LocalDate date, @RequestHeader("userId") Long userId) {
        try {
            var events = eventService.getEventsByDate(date, userId);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
        	throw e;
//            return ResponseEntity.internalServerError().body(Map.of("message", "Error retrieving events: " + e.getMessage()));
        }
    }

}
