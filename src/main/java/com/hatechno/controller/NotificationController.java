package com.hatechno.controller;

import com.hatechno.model.Notification;
import com.hatechno.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));
        String title = request.get("title");
        String message = request.get("message");
        Notification notification = notificationService.sendNotification(userId, title, message);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }
}
