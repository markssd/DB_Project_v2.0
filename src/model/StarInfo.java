package model;

public class StarInfo {
	private int occurrence_star;
	private float percentual_protostellar;
	private float percentual_prestellar;
	private float percentual_unbound;
	
	public StarInfo() {
		this.occurrence_star = 0;
		this.percentual_prestellar = 0;
		this.percentual_protostellar = 0;
		this.percentual_unbound = 0;
	}
	
	public int getOccurrence_star() {
		return occurrence_star;
	}
	public void setOccurrence_star(int occurrence_star) {
		this.occurrence_star = occurrence_star;
	}
	public float getPercentual_protostellar() {
		return percentual_protostellar;
	}
	public void setPercentual_protostellar(float percentual_protostellar) {
		this.percentual_protostellar = percentual_protostellar;
	}
	public float getPercentual_prestellar() {
		return percentual_prestellar;
	}
	public void setPercentual_prestellar(float percentual_prestellar) {
		this.percentual_prestellar = percentual_prestellar;
	}
	public float getPercentual_unbound() {
		return percentual_unbound;
	}
	public void setPercentual_unbound(float percentual_unbound) {
		this.percentual_unbound = percentual_unbound;
	}
	@Override
	public String toString() {
		return "Occurrence : "+this.occurrence_star+", PercProto:"+this.percentual_protostellar+"%, PercPrest:"+this.percentual_prestellar+"%, PercUnbo:"+this.percentual_unbound+"%";
	}
}
