package com.rohithkankipati.projects.Sked.repository;

import java.time.LocalDate;
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
	
	
    @Query("SELECT e FROM EventEntity e WHERE e.userId = :userId AND " +
    	   "e.isRepeat = false AND " +
           "(e.start <= :date AND e.end >= :date)")
    List<EventEntity> findEventsByDateAndUserId(@Param("date") LocalDate date,  @Param("userId") Long userId);
    
    @Query("SELECT e FROM EventEntity e WHERE e.userId = :userId " +
    		"AND e.isRepeat = true " +
            "AND e.repeat.mon = true " +
            "AND e.start <= :date " +
            "AND e.end >= :date")
     List<EventEntity> findMondayEventsByUserIdAndDate(Long userId, LocalDate date);

     @Query("SELECT e FROM EventEntity e WHERE e.userId = :userId " +
    		"AND e.isRepeat = true " +
            "AND e.repeat.tue = true " +
            "AND e.start <= :date " +
            "AND e.end >= :date")
     List<EventEntity> findTuesdayEventsByUserIdAndDate(Long userId, LocalDate date);

     @Query("SELECT e FROM EventEntity e WHERE e.userId = :userId " +
    		"AND e.isRepeat = true " +
            "AND e.repeat.wed = true " +
            "AND e.start <= :date " +
            "AND e.end >= :date")
     List<EventEntity> findWednesdayEventsByUserIdAndDate(Long userId, LocalDate date);

     @Query("SELECT e FROM EventEntity e WHERE e.userId = :userId " +
    		"AND e.isRepeat = true " +
            "AND e.repeat.thu = true " +
            "AND e.start <= :date " +
            "AND e.end >= :date")
     List<EventEntity> findThursdayEventsByUserIdAndDate(Long userId, LocalDate date);

     @Query("SELECT e FROM EventEntity e WHERE e.userId = :userId " +
    		"AND e.isRepeat = true " +
            "AND e.repeat.fri = true " +
            "AND e.start <= :date " +
            "AND e.end >= :date")
     List<EventEntity> findFridayEventsByUserIdAndDate(Long userId, LocalDate date);

     @Query("SELECT e FROM EventEntity e WHERE e.userId = :userId " +
    		"AND e.isRepeat = true " +
            "AND e.repeat.sat = true " +
            "AND e.start <= :date " +
            "AND e.end >= :date")
     List<EventEntity> findSaturdayEventsByUserIdAndDate(Long userId, LocalDate date);

     @Query("SELECT e FROM EventEntity e WHERE e.userId = :userId " +
    		"AND e.isRepeat = true " +
            "AND e.repeat.sun = true " +
            "AND e.start <= :date " +
            "AND e.end >= :date")
     List<EventEntity> findSundayEventsByUserIdAndDate(Long userId, LocalDate date);
	

}
