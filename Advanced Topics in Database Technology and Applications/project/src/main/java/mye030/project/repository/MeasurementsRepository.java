package mye030.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mye030.project.model.Measurements;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, String>{

}
