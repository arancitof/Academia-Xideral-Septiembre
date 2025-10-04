package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.controller;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.Notification;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Profile("!test")
public class NotificationController {

    //El controller depende del Service

    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Crear una nueva notificación")
    public ResponseEntity<Notification> createNotification(
            @RequestBody Notification notification){
        Notification created = notificationService.createNotification(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las notificaciones")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.findAllNotifications());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una notificación por su Id")
    public ResponseEntity<Notification> getNotificationById(
            @PathVariable String id) {
        return notificationService.findNotificationById(id)
                //Si lo encuenta devuelve un 200 (ok)
                .map(ResponseEntity::ok)
                //Si no lo encuentra devuelve un 404 (not found)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Obtener notificaciones por estado")
    public ResponseEntity<List<Notification>> getNotificationsByStatus(
            @RequestParam String status) {
        return ResponseEntity.ok(notificationService.findNotificationsByStatus(status));
    }

    @GetMapping
    @Operation(summary = "Obtener notificaciones por tipo")
    public ResponseEntity<List<Notification>> getNotificationsByType(
            @RequestParam String type) {
        return ResponseEntity.ok(notificationService.findNotificationsByType(type));
    }

    @GetMapping
    @Operation(summary = "Obtener notificaciones por canal de envio")
    public ResponseEntity<List<Notification>> getNotificationsByChannel(
            @RequestParam String channel) {
        return ResponseEntity.ok(notificationService.findNotificationsByChannel(channel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una notificación por su Id")
    public ResponseEntity<Void> deleteNotificationById (
            @PathVariable String id) {
        notificationService.deleteNotificationById(id);
        return ResponseEntity.noContent().build();
    }




}
