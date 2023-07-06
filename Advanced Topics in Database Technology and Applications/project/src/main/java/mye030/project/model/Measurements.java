package mye030.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "measurements")
@IdClass(CompositeKey.class)
public class Measurements {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String country_name;
	@Id
	@Column(name = "year")
	private int year;
	@Id
	@Column(name = "indicator")
	private String indicator;
	
	@Column(name = "measurement")
	private Double measurement;
	
	
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public Double getMeasurement() {
		return measurement;

	}
	public void setMeasurement(double measurement) {
		this.measurement = measurement;
	}
}
