package com.rohithkankipati.projects.Sked.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.rohithkankipati.projects.Sked.dto.EventDTO;
import com.rohithkankipati.projects.Sked.dto.RepeatDTO;

import jakarta.persistence.*;


@Entity
@Table(name = "event")
public class EventEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date end;

    @Column(name = "all_day")
    private Boolean allday;

    @Embedded
    private RepeatEntity repeat;
    
    private Boolean isRepeat;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category")
    private String category;

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

	public Boolean getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(Boolean isRepeat) {
		this.isRepeat = isRepeat;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Boolean getAllday() {
		return allday;
	}

	public void setAllday(Boolean allday) {
		this.allday = allday;
	}

	public RepeatEntity getRepeat() {
		return repeat;
	}

	public void setRepeat(RepeatEntity repeat) {
		this.repeat = repeat;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public void fromDTO(EventDTO dto) {
        this.setName(dto.getName());
        this.setDescription(dto.getDescription());
        this.setStart(Date.from(dto.getStart().toInstant()));
        this.setEnd(Date.from(dto.getEnd().toInstant()));
        this.setAllday(dto.getAllday());
        if (this.repeat == null) {
            this.repeat = new RepeatEntity();
        }
        this.repeat.fromDTO(dto.getRepeat());
        this.setCategory(dto.getCategory());
        
        RepeatDTO repeat = dto.getRepeat();
		if(repeat.getFri() || repeat.getMon() || repeat.getSat() || repeat.getSun() || repeat.getThu() || repeat.getTue() || repeat.getWed()) {
			this.setIsRepeat(true);
		} else {
			this.setIsRepeat(false);
		}
    }

    public EventDTO toDTO() {
        EventDTO dto = new EventDTO();
        dto.setId(this.getId());
        dto.setName(this.getName());
        dto.setDescription(this.getDescription());
        dto.setStart(ZonedDateTime.ofInstant(this.getStart().toInstant(), ZoneId.systemDefault()));
        dto.setEnd(ZonedDateTime.ofInstant(this.getEnd().toInstant(), ZoneId.systemDefault()));
        dto.setAllday(this.getAllday());
        dto.setRepeat(this.repeat.toDTO());
        dto.setCategory(this.getCategory());
        return dto;
    }

}
