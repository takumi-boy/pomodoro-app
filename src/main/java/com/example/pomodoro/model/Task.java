package com.example.pomodoro.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    private String userId;
    private String taskName;
    private LocalDate createdAt; // タスクの作成日

    public Task() {
        this.createdAt = LocalDate.now(); // タスク作成時の日付を設定
    }

    public Task(String userId, String taskName, LocalDate createdAt) {
        this.userId = userId;
        this.taskName = taskName;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
