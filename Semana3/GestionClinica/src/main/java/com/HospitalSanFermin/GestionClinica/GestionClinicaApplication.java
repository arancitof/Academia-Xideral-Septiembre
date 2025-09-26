package com.HospitalSanFermin.GestionClinica;

import com.HospitalSanFermin.GestionClinica.Doctors.Doctor;
import com.HospitalSanFermin.GestionClinica.Doctors.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionClinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionClinicaApplication.class, args);
    }


    @Bean
    CommandLineRunner initSlots(DoctorRepository doctor) {
        return args -> {
            if (doctor.count() == 0) {
                doctor.save(new Doctor("Carlos", "Gonzalez", "GOCL850101XX1", "5551112233", "carlos.gonzalez@mail.com", "Cardiología", true));
                doctor.save(new Doctor("Ana", "Martinez", "MARA820202XX2", "5552223344", "ana.martinez@mail.com", "Dermatología", true));
                doctor.save(new Doctor("Luis", "Fernandez", "FELA800303XX3", "5553334455", "luis.fernandez@mail.com", "Pediatría", true));
                doctor.save(new Doctor("Maria", "Lopez", "LOML880404XX4", "5554445566", "maria.lopez@mail.com", "Ginecología", true));
                doctor.save(new Doctor("Javier", "Rodriguez", "ROJJ870505XX5", "5555556677", "javier.rodriguez@mail.com", "Neurología", true));
                doctor.save(new Doctor("Laura", "Perez", "PELL890606XX6", "5556667788", "laura.perez@mail.com", "Oncología", true));
                doctor.save(new Doctor("David", "Sanchez", "SADM860707XX7", "5557778899", "david.sanchez@mail.com", "Traumatología", true));
                doctor.save(new Doctor("Sofia", "Ramirez", "RASM900808XX8", "5558889900", "sofia.ramirez@mail.com", "Endocrinología", true));
                doctor.save(new Doctor("Daniel", "Torres", "TODD840909XX9", "5559990011", "daniel.torres@mail.com", "Oftalmología", true));
                doctor.save(new Doctor("Elena", "Flores", "FLEE831010XX0", "5550001122", "elena.flores@mail.com", "Urología", true)
                );
            }
        };
    }
}



