package com.rohithkankipati.projects.Sked.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.rohithkankipati.projects.Sked.entity.EventExemptionEntity;

public interface EventExemptionRepository extends JpaRepository<EventExemptionEntity, Long> {
	
	List<EventExemptionEntity> findByEventId(Long eventId);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM EventExemptionEntity e WHERE e.event.id = :eventId")
    void deleteByEventId(Long eventId);
}	
