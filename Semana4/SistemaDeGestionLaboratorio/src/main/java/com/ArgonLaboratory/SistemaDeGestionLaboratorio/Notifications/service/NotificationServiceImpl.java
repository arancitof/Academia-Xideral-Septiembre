package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.service;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.Notification;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.NotificationChannel;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.NotificationStatus;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.NotificationType;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.repository.NotificationRepository;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.events.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("!test")
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;


    //Listener para cuando se Crea un nuevo experimento
    @ApplicationModuleListener
    public void handleExperimentCreatedEvent(ExperimentCreatedEvent event) {
        //Logger de Lombok
        log.info("Evento recibido: Nuevo experimento creado. ID: {}" , event.getExperimentId());

        //Creamos el objeto Notification
        Notification notification = Notification.builder()
                .message("Se ha Creado un nuevo Experimento: 🧪 '" + event.getExperimentName() + "'.")
                .channel(NotificationChannel.EMAIL)//Por que medio se envio la notificiacion
                .channel(NotificationChannel.SMS)
                .type(NotificationType.EXPERIMENT_CREATED)
                .status(NotificationStatus.SENT) //Suponiendo que ya se envio
                .relatedEntityType("Experiment")
                .relatedEntityId(event.getExperimentId().toString())
                .build();

        //Guardamos la notificación en la base de datos
        notificationRepository.save(notification);

        log.info("Notificacion de nuevo experimento guardadada en el historial: {}" , notification.getId());

    }

    //Listener para cuando se crea un nuevo investigador
    @ApplicationModuleListener
    public void handleInvestigatorCreatedEvent(InvestigatorCreatedEvent event) {
        //Logger de Lombok
        log.info("Evento recibido: Nuevo investigador creado. ID: {}" , event.getInvestigatorId());

        //Creamos el objeto Notificacion con el patron BUILDER
        Notification notification = Notification.builder()
                .message("Se ha dado de alta a un nuevo Investigador: 🧑‍🔬" + event.getFullName() + ".")
                .channel(NotificationChannel.EMAIL)
                .channel(NotificationChannel.SMS)
                .type(NotificationType.INVESTIGATOR_CREATED)
                .status(NotificationStatus.SENT)
                .relatedEntityType("Investigator")
                .relatedEntityId(event.getInvestigatorId().toString())
                .build();

        //Guardamos la notificación en la base de datos
        notificationRepository.save(notification);

        log.info("Notificacion de nuevo investigador guardadada en el historial: {}" , notification.getId());
    }

    //Listener para Experimentos Finalizados
    @ApplicationModuleListener
    public void handleExperimentFinalizedEvent(ExperimentFinalizedEvent event) {
        //Logger de Lombok
        log.info("Evento recibido: Experimento Finalizado. ID: {}" , event.getExperimentId());

        //Creamos el Objeto notificacion con el patron BUILDER
        Notification notification = Notification.builder()
                .message("El experimento '" + event.getExperimentName() + "Ligado a la Cedula Profesional: " + event.getInvestigatorLicenseNumber() + "'ha finalizado con exito.")
                .channel(NotificationChannel.EMAIL)
                .channel(NotificationChannel.SMS)
                .type(NotificationType.EXPERIMENT_FINALIZED)
                .status(NotificationStatus.SENT)
                .relatedEntityId("Experiment")
                .relatedEntityId(event.getExperimentId().toString())
                .build();

        //Guardamos la notificación en la base de datos
        notificationRepository.save(notification);
        log.info("Notificacion de experimento finalizado guardadada en el historial: {}" , notification.getId());
    }

    //Listener para Experimentos de Alto Riesgo
    @ApplicationModuleListener
    public void handleExperimentHighRiskEvent(ExperimentHighRiskEvent event) {
        log.info("Evento recibido: Experimento de Alto Riesgo. ID: {}" , event.getExperimentId());

        //Creamos el objeto Notification
        Notification notification = Notification.builder()
                .message("ATENCION ⚠️: El Se ha Iniciado un Experimento de alto Riesgo." + event.getExperimentName() + "Ligado a la Cedula Profesional: " + event.getInvestigatorLicenseNumber())
                .channel(NotificationChannel.EMAIL)
                .channel(NotificationChannel.SMS)
                .channel(NotificationChannel.PUSH_NOTIFICATION)
                .type(NotificationType.EXPERIMENT_HIGH_RISK)
                .status(NotificationStatus.SENT)
                .relatedEntityType("Experiment")
                .relatedEntityId(event.getExperimentId().toString())
                .build();

        //Guardamos la notificacion
        notificationRepository.save(notification);
        log.info("Notificacion de experimento de alto riesgo guardadada en el historial: {}" , notification.getId());
    }

    // Listener para la cancelación de Experimentos
    @ApplicationModuleListener
    public void handleExperimentCancelledEvent(ExperimentCancelledEvent event) {
        log.info("Evento recibido: Experimento cancelado. ID: {}", event.getExperimentId());

        Notification notification = Notification.builder()
                .message("El experimento '" + event.getExperimentName() + "' ha sido cancelado.")
                .channel(NotificationChannel.EMAIL)
                .type(NotificationType.EXPERIMENT_CANCELLED)
                .status(NotificationStatus.SENT)
                .relatedEntityType("Experiment")
                .relatedEntityId(event.getExperimentId().toString())
                .build();

        notificationRepository.save(notification);
        log.info("Notificación de cancelación guardada en el historial. ID de notificación: {}", notification.getId());
    }

}
