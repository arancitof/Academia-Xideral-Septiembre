package com.HospitalSanFermin.GestionClinica.SharedNotifications;

import com.HospitalSanFermin.GestionClinica.appointments.Cita;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;


public class CitaAgendadaEvent extends ApplicationEvent {

    private final Cita cita;

    public CitaAgendadaEvent(Object source, Cita cita){
        super(cita);
        this.cita = cita;
    }

    public Cita getCita(){
        return cita;
    }



}

