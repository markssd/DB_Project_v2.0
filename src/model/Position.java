package model;

public class Position {
	private double g_lon;
	private double g_lat;
	
	public Position() {
		this.g_lon = 0;
		this.g_lat = 0;
	}
	public Position(double lon, double lat) {
		this.g_lon = lon;
		this.g_lat = lat;
	}
	public double getG_lon() {
		return g_lon;
	}
	
	public void setG_lon(double g_lon) {
		this.g_lon = g_lon;
	}
	
	public double getG_lat() {
		return g_lat;
	}
	
	public void setG_lat(double g_lat) {
		this.g_lat = g_lat;
	}
	
	@Override
	public String toString() {
		return "Longitude :"+this.getG_lon()+" Latitude: "+this.getG_lat();
	}
	
}
