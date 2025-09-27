package com.HospitalSanFermin.GestionClinica.SharedNotifications;

import com.HospitalSanFermin.GestionClinica.appointments.Cita;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @EventListener
    public void handleCitaAgendadaEvent(CitaAgendadaEvent event){
        Cita cita = event.getCita();

        notificarDoctor(cita);
        notificarPaciente(cita);

    }



    //Aquí se notifica a los doctores

    public void notificarDoctor(Cita cita) {

        System.out.println("HOSPITAL SF 🏥: Estimado: " + cita.getDoctor().getApellido());
        System.out.println(" Tiene una nueva cita agendada con el paciente: " + cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido());
        System.out.println(" con Fecha y hora: " + cita.getFechaHora());
    }

    //Aquí se notifica al paciente
    private void notificarPaciente(Cita cita) {
        System.out.println("HOSPITAL SF ✉️: Estimado: " + cita.getPaciente().getApellido());
        System.out.println(" Su cita ha sido confirmada con el Dr. " + cita.getDoctor().getApellido());
        System.out.println(" Con Fecha y hora: " + cita.getFechaHora());
        System.out.println(" Se ha enviado un correo con todos los detalles a la dirección: " + cita.getPaciente().getEmail());
    }
    }

