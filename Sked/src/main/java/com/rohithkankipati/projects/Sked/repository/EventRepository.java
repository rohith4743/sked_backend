package com.rohithkankipati.projects.Sked.repository;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rohithkankipati.projects.Sked.entity.EventEntity;


@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
	

	Optional<EventEntity> findByIdAndUserId(Long id, Long userId);

     @Query("SELECT e FROM EventEntity e WHERE e.userId = :userId AND e.start < :endOfDay AND" +
             "((e.isRepeat = false AND e.end > :startOfDay) OR " +
             "(e.isRepeat = true AND (e.repeatNever = true OR e.repeatEndDate >= :endOfDay)))")
     List<EventEntity> findByUserIdandDate(Long userId, @Param("startOfDay") ZonedDateTime startOfDay, @Param("endOfDay") ZonedDateTime endOfDay);
}
