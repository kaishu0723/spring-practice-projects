package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.TaskItem;
import com.example.demo.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskController {

	private final TaskService service;
	private final List<TaskItem> list=new ArrayList<>();
	private static long nextId=1;
	
	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("tasks",list);
        model.addAttribute("taskItem", new TaskItem());
		return "main";
	}
	
	@PostMapping("/add-task")
	public String addTask(@ModelAttribute TaskItem newTask) {
		newTask.setId(nextId++);
		list.add(newTask);
		return "redirect:/";
	}
	
	@PostMapping("/toggle")
	public String toggle(@RequestParam("id") long id) {
		// IDでタスクを検索する
        Optional<TaskItem> taskOptional = list.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
        
        // タスクが見つかった場合のみ処理を実行
        taskOptional.ifPresent(task -> task.setIsComplete(!task.getIsComplete()));
		return "redirect:/";
	}
}
