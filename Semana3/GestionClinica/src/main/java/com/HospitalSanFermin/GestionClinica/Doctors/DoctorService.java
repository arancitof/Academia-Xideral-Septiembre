package com.HospitalSanFermin.GestionClinica.Doctors;


import com.HospitalSanFermin.GestionClinica.SharedNotifications.CitaAgendadaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    /*Aquí Mostramos lo que queremos manejar*/

    //De donde tomamos los doctores?
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    //Metodo para verificar que doctores están disponles
    public List<Doctor> obtenerDoctoresDisponibles() {
        return doctorRepository.findByDisponible(true);
    }

}
