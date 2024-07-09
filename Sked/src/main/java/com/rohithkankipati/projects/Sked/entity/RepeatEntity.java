package com.rohithkankipati.projects.Sked.entity;

import com.rohithkankipati.projects.Sked.dto.RepeatDTO;

import jakarta.persistence.Embeddable;

@Embeddable
public class RepeatEntity {

    private Boolean sun;
    private Boolean mon;
    private Boolean tue;
    private Boolean wed;
    private Boolean thu;
    private Boolean fri;
    private Boolean sat;

    public Boolean getSun() {
        return sun;
    }

    public void setSun(Boolean sun) {
        this.sun = sun;
    }

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
    
    public void fromDTO(RepeatDTO dto) {
        this.setMon(dto.getMon());
        this.setTue(dto.getTue());
        this.setWed(dto.getWed());
        this.setThu(dto.getThu());
        this.setFri(dto.getFri());
        this.setSat(dto.getSat());
        this.setSun(dto.getSun());
    }

    public RepeatDTO toDTO() {
        RepeatDTO dto = new RepeatDTO();
        dto.setMon(this.getMon());
        dto.setTue(this.getTue());
        dto.setWed(this.getWed());
        dto.setThu(this.getThu());
        dto.setFri(this.getFri());
        dto.setSat(this.getSat());
        dto.setSun(this.getSun());
        return dto;
    }
}

