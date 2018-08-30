package model;

public class Filament {
	private int id;
	private String name;
	private double total_flux;
	private double mean_dens;
	private double mean_temp;
	private double ellipticity;
	private double contrast;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTotal_flux() {
		return total_flux;
	}
	public void setTotal_flux(double total_flux) {
		this.total_flux = total_flux;
	}
	public double getMean_dens() {
		return mean_dens;
	}
	public void setMean_dens(double mean_dens) {
		this.mean_dens = mean_dens;
	}
	public double getMean_temp() {
		return mean_temp;
	}
	public void setMean_temp(double mean_temp) {
		this.mean_temp = mean_temp;
	}
	public double getEllipticity() {
		return ellipticity;
	}
	public void setEllipticity(double ellipticity) {
		this.ellipticity = ellipticity;
	}
	public double getContrast() {
		return contrast;
	}
	public void setContrast(double contrast) {
		this.contrast = contrast;
	}
	@Override
	public String toString() {
		return "id : "+this.getId()+", name : "+ this.getName();
	}
}
