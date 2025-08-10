package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile ,Long> {
	List<Profile> findByUser_IdOrderByDateAsc(Long userId);
}
