package com.example.pomodoro.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.time.Instant;

@Service
public class PomodoroService {
    private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private ScheduledFuture<?> currentTask;

    public PomodoroService() {
        scheduler.initialize();
    }

    public void startTimer(Runnable task, long durationMinutes) {
        stopTimer();
        Instant startTime = Instant.now().plusMillis(TimeUnit.MINUTES.toMillis(durationMinutes));
        currentTask = scheduler.schedule(task, startTime);
    }

    public void stopTimer() {
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel(true);
        }
    }
}
