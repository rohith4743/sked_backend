package com.rohithkankipati.projects.Sked.service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
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
import com.rohithkankipati.projects.Sked.dto.RepeatDTO;
import com.rohithkankipati.projects.Sked.entity.EventEntity;
import com.rohithkankipati.projects.Sked.entity.EventExemptionEntity;
import com.rohithkankipati.projects.Sked.exception.SkedException;
import com.rohithkankipati.projects.Sked.repository.EventExemptionRepository;
import com.rohithkankipati.projects.Sked.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private EventExemptionRepository exemptionRepository;
	
	@Autowired
	private Logger logger;
	
	public Map<String, Object> addEvent(EventDTO eventDTO, Long userId) throws SkedException {
		
		
        if (eventDTO.getStart().isAfter(eventDTO.getEnd())) {
            throw new SkedException("event.service.startBeforeEnd", HttpStatus.BAD_REQUEST);
        }
        
        RepeatDTO rep = eventDTO.getRepeat();
        if (rep.getSun() || rep.getMon() || rep.getTue() || rep.getWed() || rep.getThu() || rep.getFri() || rep.getSat()) {
        	if (Duration.between(eventDTO.getStart(), eventDTO.getEnd()).toHours() > 24) {
        		throw new SkedException("event.service.recurringGreaterthan24Hours", HttpStatus.BAD_REQUEST);
        	}
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
	
	public Map<String, Object> editEvent(EventDTO eventDTO, Long userId, Long eventId) throws SkedException {
        
		
		Optional<EventEntity> optional = eventRepository.findByIdAndUserId(eventId, userId);
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
	
	public Map<String, Object> deleteEventFromRecurring(Long userId, Long eventId, ZonedDateTime date) throws SkedException {
		
		Optional<EventEntity> optional = eventRepository.findByIdAndUserId(eventId, userId);
		EventEntity event = optional.orElseThrow(() -> new SkedException("event.service.notFound", HttpStatus.NOT_FOUND));
		
		EventExemptionEntity exemption = new EventExemptionEntity();
		exemption.setDate(date);
		exemption.setEvent(event);
		
		try {
			exemptionRepository.save(exemption);	
		} catch (Exception e) {
            logger.error("Error deleting event: " + e.getMessage(), e);
            throw new SkedException("event.service.notDeleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		return Map.of("message", "Event Deleted successfully");
	}
	
	
	public List<EventDTO> getEventsbyDate(ZonedDateTime date, Long userId) throws SkedException {
		
		try {
			
			ZonedDateTime startOfDay = date
	                .with(ChronoField.HOUR_OF_DAY, 0)
	                .with(ChronoField.MINUTE_OF_HOUR, 0)
	                .with(ChronoField.SECOND_OF_MINUTE, 0)
	                .with(ChronoField.MILLI_OF_SECOND, 0);
	    
	    	ZonedDateTime endOfDay = date
	                .with(ChronoField.HOUR_OF_DAY, 23)
	                .with(ChronoField.MINUTE_OF_HOUR, 59)
	                .with(ChronoField.SECOND_OF_MINUTE, 59);
	    	
	    	List<EventEntity> events = eventRepository.findByUserIdandDate(userId, startOfDay, endOfDay).stream().filter(
	    			event -> event.isEventOnDate(startOfDay, endOfDay)
	    			).collect(Collectors.toList());

	    	
	    	List<EventDTO> eventDTOs = new ArrayList<>();
	    	
	    	for (EventEntity eventEntity : events) {
	    		
				List<EventExemptionEntity> exemptions = exemptionRepository.findByEventId(eventEntity.getId());
			
				if(exemptions.size() == 0) {
					eventDTOs.add(eventEntity.toDTO());
				} else if(eventEntity.getStart().toLocalDate().equals(eventEntity.getEnd().toLocalDate())) {
					
					Boolean found = true;
					for (EventExemptionEntity exempt : exemptions) {
						
						if(exempt.getDate().toLocalDate().equals(startOfDay.toLocalDate())) {
							found = false;
						}
						
					}
					if(found) eventDTOs.add(eventEntity.toDTO());
					
				} else {
					Boolean found = true;
					for (EventExemptionEntity exempt : exemptions) {
						
						if(exempt.getDate().toLocalDate().equals(startOfDay.minusDays(1).toLocalDate())) {
							found = false;
						}
						
					}
					if (found) eventDTOs.add(eventEntity.toDTO());
				}
				
			}
	    	
	    	return eventDTOs;
	    	
			
		} catch (Exception e) {
			logger.error("Error retrieving events for date: " + date + " and userId: " + userId, e);
	        throw new SkedException("event.service.getError", HttpStatus.BAD_REQUEST);
		}
		
	}

}
