package model;

public class Star extends Position {
	private int id;
	private String name;
	private String type;
	private double flux;
	private double distance_branch;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getFlux() {
		return flux;
	}
	public void setFlux(double flux) {
		this.flux = flux;
	}
	public double getDistanceBranch() {
		return distance_branch;
	}
	public void setDistanceBranch(double distance_branch) {
		this.distance_branch = distance_branch;
	}
	
	@Override
	public String toString() {
		String print = "name: "+this.name+"flux: "+this.flux+" distance: "+this.distance_branch;
		//String print = "id: "+this.id+"; name: "+this.name+"; type: "+this.type+"; flux: "+this.flux+"; lat: "+this.getG_lat()+"; long: "+this.getG_lon()+";";
		return print;
	}
}
