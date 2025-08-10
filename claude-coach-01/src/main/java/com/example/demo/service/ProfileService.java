package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Profile;
import com.example.demo.repository.ProfileRepository;

@Service
public class ProfileService {
	@Autowired
	private ProfileRepository profileRepository;
	
	public List<Profile> getProfileByUser(Long userId){
		return profileRepository.findByUser_IdOrderByDateAsc(userId);
	}
	
	public void saveProfile(Profile profile) {
		profileRepository.save(profile);
	}
}
