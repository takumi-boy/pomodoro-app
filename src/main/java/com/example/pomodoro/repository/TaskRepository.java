package com.example.pomodoro.repository;

import com.example.pomodoro.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUserId(String userId); // ユーザーIDでタスクを取得
}
