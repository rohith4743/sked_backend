package com.rohithkankipati.projects.Sked.entity;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

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
    private ZonedDateTime start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private ZonedDateTime end;

    @Column(name = "all_day")
    private Boolean allday;

    @Embedded
    private RepeatEntity repeat;
    
    private Boolean isRepeat;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category")
    private String category;
    
    private Boolean repeatNever;
    
    private ZonedDateTime repeatEndDate;

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
	
	public Boolean getRepeatNever() {
		return repeatNever;
	}

	public void setRepeatNever(Boolean repeatNever) {
		this.repeatNever = repeatNever;
	}

	public ZonedDateTime getRepeatEndDate() {
		return repeatEndDate;
	}

	public void setRepeatEndDate(ZonedDateTime repeatEndDate) {
		this.repeatEndDate = repeatEndDate;
	}

	public void fromDTO(EventDTO dto) {
        this.setName(dto.getName());
        this.setDescription(dto.getDescription());
        this.setStart(dto.getStart());
        this.setEnd(dto.getEnd());
        this.setAllday(dto.getAllday());
        if (this.repeat == null) {
            this.repeat = new RepeatEntity();
        }
        this.repeat.fromDTO(dto.getRepeat());
        this.setCategory(dto.getCategory());
        this.setRepeatNever(dto.getRepeatNever());
        this.setRepeatEndDate(dto.getRepeatEndDate());
        
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
        dto.setStart(this.getStart());
        dto.setEnd(this.getEnd());
        dto.setAllday(this.getAllday());
        dto.setRepeat(this.repeat.toDTO());
        dto.setCategory(this.getCategory());
        dto.setRepeatNever(this.repeatNever);
        dto.setRepeatEndDate(this.repeatEndDate);
        return dto;
    }
    
    public boolean isEventOnDate(ZonedDateTime startOfDay, ZonedDateTime endOfDay) {
        
    	if (!this.isRepeat) {
            
            return this.start.isBefore(endOfDay) && this.end.isAfter(startOfDay);
            
        } else {
            
            if (this.repeatEndDate != null && this.repeatEndDate.isBefore(startOfDay)) {
                return false;
            }
            
         
            
            DayOfWeek dayOfWeek = startOfDay.getDayOfWeek();
            

            boolean isRepeatingOnCurrentDay = false;
            switch (dayOfWeek) {
                case SUNDAY: isRepeatingOnCurrentDay = this.getRepeat().getSun();break;
                case MONDAY: isRepeatingOnCurrentDay = this.getRepeat().getMon();break;
                case TUESDAY: isRepeatingOnCurrentDay = this.getRepeat().getTue();break;
                case WEDNESDAY: isRepeatingOnCurrentDay = this.getRepeat().getWed();break;
                case THURSDAY: isRepeatingOnCurrentDay = this.getRepeat().getThu();break;
                case FRIDAY: isRepeatingOnCurrentDay = this.getRepeat().getFri();break;
                case SATURDAY: isRepeatingOnCurrentDay = this.getRepeat().getSat();break;                
            }
            
            if (isRepeatingOnCurrentDay) return true;
            
            DayOfWeek previousDayofWeek = startOfDay.minusDays(1).getDayOfWeek();
            boolean isRepeatingOnPreviousDay = false;
            switch (previousDayofWeek) {
                case SUNDAY: isRepeatingOnCurrentDay = this.getRepeat().getSun();break;
                case MONDAY: isRepeatingOnCurrentDay = this.getRepeat().getMon();break;
                case TUESDAY: isRepeatingOnCurrentDay = this.getRepeat().getTue();break;
                case WEDNESDAY: isRepeatingOnCurrentDay = this.getRepeat().getWed();break;
                case THURSDAY: isRepeatingOnCurrentDay = this.getRepeat().getThu();break;
                case FRIDAY: isRepeatingOnCurrentDay = this.getRepeat().getFri();break;
                case SATURDAY: isRepeatingOnCurrentDay = this.getRepeat().getSat();break;                
            }
            
            if(!start.toLocalDate().equals(end.toLocalDate()) && isRepeatingOnPreviousDay) return true;
              
        }

         return false;
        
    }
    
    @Override
    public String toString() {
        return "EventEntity {" +
                "\n\tid=" + id + 
                ",\n\tname='" + name + '\'' +
                ",\n\tstart=" + start +
                ",\n\tend=" + end +
                ",\n\tallday=" + allday +
                ",\n\trepeat=" + repeat +
                ",\n\tisRepeat=" + isRepeat +
                ",\n\tuserId=" + userId +
                ",\n\trepeatNever=" + repeatNever +
                ",\n\trepeatEndDate=" + repeatEndDate +
                "\n}";
    }

    
    

}
