package it.polito.tdp.baseball.model;

public class Adiacenza {
	
	public Adiacenza(People v1, People v2) {
		super();
		this.v1 = v1;
		this.v2 = v2;
	}
	private People v1;
	private People v2;
	public People getV1() {
		return v1;
	}
	public void setV1(People v1) {
		this.v1 = v1;
	}
	public People getV2() {
		return v2;
	}
	public void setV2(People v2) {
		this.v2 = v2;
	}

}
