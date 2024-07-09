package com.rohithkankipati.projects.Sked.dto;

import java.time.ZonedDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EventDTO {
	
	private Long id;
	
    @NotBlank(message = "event.name.empty")
    @Size(max = 100, message = "event.name.large")
	private String name; 
	
    @Size(max = 500, message = "event.description.large")
	private String description;
	
	private String category;
	
	private Boolean allday;
	
	@Future(message = "event.start.future")
    @NotNull(message = "event.start.empty")
	private ZonedDateTime start;
	
	@Future(message = "event.end.future")
    @NotNull(message = "event.end.empty")
	private ZonedDateTime end;
	
    @Valid
	private RepeatDTO repeat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getAllday() {
		return allday;
	}

	public void setAllday(Boolean allday) {
		this.allday = allday;
	}

	public ZonedDateTime getStart() {
		return start;
	}

	public void setStart(ZonedDateTime start) {
		this.start = start;
	}

	public ZonedDateTime getEnd() {
		return end;
	}

	public void setEnd(ZonedDateTime end) {
		this.end = end;
	}

	public RepeatDTO getRepeat() {
		return repeat;
	}

	public void setRepeat(RepeatDTO repeat) {
		this.repeat = repeat;
	}
	
}