package com.HospitalSanFermin.GestionClinica.Doctors;


import com.HospitalSanFermin.GestionClinica.SharedNotifications.CitaAgendadaEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @EventListener
    public void handleCitaAgendadaEvent(CitaAgendadaEvent event) {
        Long doctorId = event.getCita().getDoctor().getId();
        String nombrePaciente = event.getCita().getPaciente().getNombre();

        System.out.println("HOSPITAL SF 🏥: Nueva cita agendada!");
        System.out.println("Doctor con ID: " + doctorId + " tiene una nueva cita con el paciente: " + nombrePaciente);
        System.out.println("Fecha y hora de la cita: " + event.getCita().getFechaHora());
    }
}
