package com.example.pomodoro.controller;

import com.example.pomodoro.model.Task;
import com.example.pomodoro.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public String getTasks(HttpSession session, Model model) {
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }

     // 全タスクを取得し、日付ごとにグループ化
        List<Task> tasks = taskRepository.findByUserId(userId.toString());
        Map<LocalDate, List<Task>> groupedTasks = tasks.stream()
            .collect(Collectors.groupingBy(Task::getCreatedAt));

        model.addAttribute("groupedTasks", groupedTasks);
        return "tasks";
    }

    @PostMapping("/save")
    public String saveTask(@RequestParam String taskName, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        LocalDate today = LocalDate.now(); // 自動的に今日の日付を設定
        Task task = new Task(userId.toString(), taskName, today);
        taskRepository.save(task);
        return "redirect:/tasks";
    }
}
