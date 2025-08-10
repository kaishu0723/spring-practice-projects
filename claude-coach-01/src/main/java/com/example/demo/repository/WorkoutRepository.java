package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Workout;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout,Long> {
	List<Workout> findByUser_IdOrderByDateDesc(Long userId);
}
