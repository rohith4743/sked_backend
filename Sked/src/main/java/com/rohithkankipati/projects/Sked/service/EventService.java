package com.rohithkankipati.projects.Sked.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rohithkankipati.projects.Sked.dto.EventDTO;
import com.rohithkankipati.projects.Sked.entity.EventEntity;
import com.rohithkankipati.projects.Sked.exception.SkedException;
import com.rohithkankipati.projects.Sked.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private Logger logger;
	
	public Map<String, Object> addEvent(EventDTO eventDTO, Long userId) throws SkedException {
		
		
        if (eventDTO.getStart().isAfter(eventDTO.getEnd())) {
            throw new SkedException("event.service.startBeforeEnd", HttpStatus.BAD_REQUEST);
        }
		
		EventEntity eventEntity = new EventEntity();
		eventEntity.fromDTO(eventDTO);
		eventEntity.setUserId(userId);
		
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			EventEntity saved = eventRepository.save(eventEntity);
			response.put("id", saved.getId());
			response.put("message", "Event added Successfully");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SkedException("event.service.notAdded", HttpStatus.BAD_REQUEST);
			
		}
		return response;
	}
	
	public Map<String, Object> editEvent(EventDTO eventDTO, Long userId) throws SkedException {
        Optional<EventEntity> optional = eventRepository.findByIdAndUserId(eventDTO.getId(), userId);
        EventEntity existingEvent = optional.orElseThrow(() -> new SkedException("event.service.notFound", HttpStatus.NOT_FOUND));
        
        existingEvent.fromDTO(eventDTO);
        
        try {
            eventRepository.save(existingEvent);
        } catch (Exception e) {
            logger.error("Error updating event: " + e.getMessage(), e);
            throw new SkedException("event.service.notUpdated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return Map.of("message", "Event updated successfully");
    }
	
	public Map<String, Object> deleteEvent(Long eventId, Long userId) throws SkedException {
        
		Optional<EventEntity> optional = eventRepository.findByIdAndUserId(eventId, userId);
		EventEntity event = optional.orElseThrow(() -> new SkedException("event.service.notFound", HttpStatus.NOT_FOUND));
        
        try {
            eventRepository.delete(event);
        } catch (Exception e) {
            logger.error("Error deleting event: " + e.getMessage(), e);
            throw new SkedException("event.service.notDeleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return Map.of("message", "Event Deleted successfully");
    }
	
	public List<EventDTO> getEventsByDate(LocalDate date, Long userId) throws SkedException{
		
		try {
			String day = date.getDayOfWeek().toString().substring(0, 3).toLowerCase();
			List<EventEntity> events = null;
			
			
			switch (day) {
				case "sun": events = eventRepository.findSundayEventsByUserIdAndDate(userId, date); break;
				case "mon" : events = eventRepository.findMondayEventsByUserIdAndDate(userId, date);break;
				case "tue": events = eventRepository.findTuesdayEventsByUserIdAndDate(userId, date);break;
				case "wed" : events = eventRepository.findWednesdayEventsByUserIdAndDate(userId, date);break;
				case "thu": events = eventRepository.findThursdayEventsByUserIdAndDate(userId, date);break;
				case "fri" : events = eventRepository.findFridayEventsByUserIdAndDate(userId, date);break;
				case "sat": events = eventRepository.findSaturdayEventsByUserIdAndDate(userId, date);break;
					
				default:
					throw new IllegalArgumentException("Unexpected value: " + day);
			}
			
			
			List<EventEntity> nonRepeatEvents = eventRepository.findEventsByDateAndUserId(date, userId);
			events.addAll(nonRepeatEvents);
	        return events.stream().map(EventEntity::toDTO).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SkedException("event.service.getError", HttpStatus.BAD_REQUEST);
		}
    }

}
