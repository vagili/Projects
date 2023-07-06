package mye030.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mye030.project.model.Measurements;
import mye030.project.repository.MeasurementsRepository;

@Service
public class MeasurementsServiceImpl implements MeasurementsService{

	@Autowired
	private MeasurementsRepository measurementsRepository;
	
	@Override
	public List<Measurements> getAllMeasurements(){
		return measurementsRepository.findAll();
	}
	/*@Override
	public List<Measurements> saveAllMeasurements(List<Measurements> list){
		measurementsRepository.saveAll(list);
		return measurementsRepository.findAll();
	}*/

}
