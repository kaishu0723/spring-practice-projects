package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Profile;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProfileService;

@Controller
@RequestMapping("/profiles")
public class ProfileController {
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public String showProfiles(Model model) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		
		User user=userRepository.findByUsername(username).orElse(null);
		if(user != null) {
			List<Profile> profiles=profileService.getProfileByUser(user.getId());
			model.addAttribute("profiles",profiles);
			model.addAttribute("newProfile",new Profile());
		}
		
		return "profile/list";
	}
	
	@PostMapping
	public String saveProfile(@ModelAttribute("newProfile") Profile profile) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		User user=userRepository.findByUsername(username).orElse(null);
		
		if(user != null) {
			profile.setUser(user);
			profileService.saveProfile(profile);
		}
		
		return "redirect:/profiles";
	}
}
