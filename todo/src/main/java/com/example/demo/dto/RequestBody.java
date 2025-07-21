package com.example.demo.dto;

import lombok.Data;

@Data
public class RequestBody {
	private String taskTitle;
	private Boolean taskComplete;
}
