package com.rohithkankipati.projects.Sked.dto;

import jakarta.validation.constraints.NotNull;

public class RepeatDTO {
	
	@NotNull(message = "{event.day.unspecified}")
	private Boolean mon;
	
    @NotNull(message = "{event.day.unspecified}")
	private Boolean tue;
	
    @NotNull(message = "{event.day.unspecified}")
	private Boolean wed;
	
    @NotNull(message = "{event.day.unspecified}")
	private Boolean thu;
	
    @NotNull(message = "{event.day.unspecified}")
	private Boolean fri;
	
    @NotNull(message = "{event.day.unspecified}")
	private Boolean sat;
	
    @NotNull(message = "{event.day.unspecified}")
	private Boolean sun;

	public Boolean getMon() {
		return mon;
	}

	public void setMon(Boolean mon) {
		this.mon = mon;
	}

	public Boolean getTue() {
		return tue;
	}

	public void setTue(Boolean tue) {
		this.tue = tue;
	}

	public Boolean getWed() {
		return wed;
	}

	public void setWed(Boolean wed) {
		this.wed = wed;
	}

	public Boolean getThu() {
		return thu;
	}

	public void setThu(Boolean thu) {
		this.thu = thu;
	}

	public Boolean getFri() {
		return fri;
	}

	public void setFri(Boolean fri) {
		this.fri = fri;
	}

	public Boolean getSat() {
		return sat;
	}

	public void setSat(Boolean sat) {
		this.sat = sat;
	}

	public Boolean getSun() {
		return sun;
	}

	public void setSun(Boolean sun) {
		this.sun = sun;
	}
	
	
	
}

