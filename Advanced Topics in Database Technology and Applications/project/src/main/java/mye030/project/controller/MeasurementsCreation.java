package mye030.project.controller;

import java.util.ArrayList;
import java.util.List;

import mye030.project.model.Measurements;

public class MeasurementsCreation {
	
	public List<Measurements> newmes = new ArrayList<Measurements>();;

    // default and parameterized constructor
	

    public void addMeasurement(Measurements newMeasurements) {
        newmes.add(newMeasurements);
    }

	public List<Measurements> getNewmes() {
		return newmes;
	}

	public void setNewmes(List<Measurements> newmes) {
		this.newmes = newmes;
	}
	
    public int getSize() {
    	return this.newmes.size();
    }
}
