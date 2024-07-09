package com.rohithkankipati.projects.Sked.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

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

    @Column(name = "repeat_days")  // Store as a single string
    private String repeat;

    @Column(name = "username")
    private String username;

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

	public String[] getRepeat() {
        return repeat != null ? repeat.split(",") : new String[0];
    }

    public void setRepeat(String[] repeat) {
        this.repeat = repeat == null ? null : Arrays.stream(repeat).collect(Collectors.joining(","));
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
