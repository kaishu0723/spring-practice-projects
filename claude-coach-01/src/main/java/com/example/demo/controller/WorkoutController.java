package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.domain.Workout;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.WorkoutService;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {
	@Autowired
	private WorkoutService workoutService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public String showWorkouts(Model model) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		User user = userRepository.findByUsername(username).orElse(null);
		if(user != null) {
			List<Workout> workouts=workoutService.getWorkoutsByUser(user.getId());
			model.addAttribute("workouts",workouts);
		}
		
		return "workout/list";
	}
	
	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("workout",new Workout());
		return "workout/add";
	}
	
	@PostMapping
	public String addWorkout(@ModelAttribute("workout") Workout workout) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		User user=userRepository.findByUsername(username).orElse(null);
		if(user != null) {
			workout.setUser(user);
			workoutService.saveWorkout(workout);
		}
		
		return "redirect:/workouts";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id,Model model) {
		Optional<Workout> workout = workoutService.getWorkoutById(id);
		if(workout.isPresent()) {
			model.addAttribute("workout",workout.get());
			return "workout/edit";
		}
		return "redirect:/workouts";
	}
	
	@PostMapping("/update")
	public String updateWorkout(@ModelAttribute Workout workout) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		User user=userRepository.findByUsername(username).orElse(null);
		if(user != null) {
			workout.setUser(user);
			workoutService.saveWorkout(workout);
		}
		return "redirect:/workouts";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteWorkout(@PathVariable Long id) {
		workoutService.deleteWorkout(id);
		return "redirect:/workouts";
	}
}
