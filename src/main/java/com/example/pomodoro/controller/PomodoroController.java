package com.example.pomodoro.controller;

import com.example.pomodoro.service.PomodoroService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timer")
public class PomodoroController {
    private final PomodoroService pomodoroService;

    public PomodoroController(PomodoroService pomodoroService) {
        this.pomodoroService = pomodoroService;
    }

    @PostMapping("/start")
    public String startTimer(@RequestParam long minutes) {
        pomodoroService.startTimer(() -> {
        	//タイマー終了時の処理
            System.out.println("Timer ended!");
        }, minutes);
        return "Timer started for " + minutes + " minutes.";
    }

    @PostMapping("/stop")
    public String stopTimer() {
        pomodoroService.stopTimer();
        return "Timer stopped.";
    }
}
