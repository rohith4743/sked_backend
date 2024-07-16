package com.rohithkankipati.projects.Sked.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohithkankipati.projects.Sked.entity.EventExemptionEntity;

public interface EventExemptionRepository extends JpaRepository<EventExemptionEntity, Long> {
	
	List<EventExemptionEntity> findByEventId(Long eventId);
}	
