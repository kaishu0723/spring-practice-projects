package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Workout;
import com.example.demo.repository.WorkoutRepository;

@Service
public class WorkoutService {
	@Autowired
	private WorkoutRepository workoutRepository;
	
	public List<Workout> getWorkoutsByUser(Long userId){
		return workoutRepository.findByUser_IdOrderByDateDesc(userId);
	}
	
	public Optional<Workout> getWorkoutById(Long id){
		return workoutRepository.findById(id);
	}
	
	public Workout saveWorkout(Workout workout) {
		return workoutRepository.save(workout);
	}
	
	public void deleteWorkout(Long id) {
		workoutRepository.deleteById(id);
	}
}
