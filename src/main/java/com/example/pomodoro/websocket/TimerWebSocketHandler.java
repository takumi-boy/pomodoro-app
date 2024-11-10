package com.example.pomodoro.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Timer;
import java.util.TimerTask;

public class TimerWebSocketHandler extends TextWebSocketHandler {

    private Timer timer;
    private int remainingTime;
    private int workingTime = 25 * 60; // 作業タイマーの初期値（秒）
    private int breakTime = 5 * 60; // 休憩タイマーの初期値（秒）
    private boolean isBreakTime = false; // 休憩タイマーかどうかを判定
    private boolean isRunning = false; // タイマーが実行中かどうかを判定

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String command = message.getPayload();
        if ("start".equals(command)) {
            if (!isRunning) {
                startTimer(session, remainingTime > 0 ? remainingTime : (isBreakTime ? breakTime : workingTime)); // 状態に応じて再開または初期値に設定
            }
        } else if ("stop".equals(command)) {
            stopTimer();
        } else if ("reset".equals(command)) {
            resetTimer(session);
        }
    }

    private void startTimer(WebSocketSession session, int durationInSeconds) {
        stopTimer(); // 既存のタイマーを停止
        remainingTime = durationInSeconds;
        isRunning = true;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (remainingTime > 0) {
                        int minutes = remainingTime / 60;
                        int seconds = remainingTime % 60;
                        String timeMessage = String.format("%02d:%02d", minutes, seconds);
                        session.sendMessage(new TextMessage(timeMessage));
                        remainingTime--;
                    } else {
                        timer.cancel();
                        isRunning = false;
                        if (!isBreakTime) {
                            // 作業タイマーが終了した場合
                            session.sendMessage(new TextMessage("Time's up! Starting break..."));
                            startTimer(session, breakTime); // 休憩タイマーを開始
                            isBreakTime = true;
                        } else {
                            // 休憩タイマーが終了した場合
                            session.sendMessage(new TextMessage("Break time's up! Starting work..."));
                            startTimer(session, workingTime); // 作業タイマーを再開
                            isBreakTime = false;
                        }
                    }
                } catch (Exception e) {
                    timer.cancel();
                    isRunning = false;
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        isRunning = false;
    }

    private void resetTimer(WebSocketSession session) {
        stopTimer(); // タイマーを停止

        if (isBreakTime) {
            remainingTime = breakTime; // 休憩タイマーの初期値にリセット
            String timeMessage = String.format("%02d:00", breakTime / 60); // 休憩時間の初期値を送信
            try {
                session.sendMessage(new TextMessage(timeMessage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            remainingTime = workingTime; // 作業タイマーの初期値にリセット
            String timeMessage = String.format("%02d:00", workingTime / 60); // 作業時間の初期値を送信
            try {
                session.sendMessage(new TextMessage(timeMessage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        isRunning = false; // タイマーは停止状態にする
    }
}
