package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	void testRegisterNewUser() {
		User user=new User();
		user.setUsername("testUser");
		user.setPassword("password123");
		
		when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		User registeredUser=userService.registerNewUser(user);
		
		assertEquals("hashedPassword",registeredUser.getPassword());
		verify(passwordEncoder,times(1)).encode("password123");
		verify(userRepository,times(1)).save(any(User.class));
	}
}
