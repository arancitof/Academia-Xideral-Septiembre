package com.HospitalSanFermin.GestionClinica.Doctors;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository <Doctor, Long>{

    List<Doctor> findByDisponible(boolean disponible);
    Optional<Doctor> findFirstByNombreAndEspecialidadAndDisponible(String nombre, String especialidad, boolean disponible);

}
