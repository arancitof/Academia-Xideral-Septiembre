package com.HospitalSanFermin.GestionClinica.Doctors;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository <Doctor, Long>{

    List<Doctor> findByDisponible(boolean disponible);

}
