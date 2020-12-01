package com.skole.project.entity;

public class Predmet {
	private Integer idPredmet;
	private String nazivPredmet;
	private Boolean active;
	
	public Predmet(Integer idPredmet, String nazivPredmet, Boolean active) {
		super();
		this.idPredmet = idPredmet;
		this.nazivPredmet = nazivPredmet;
		this.active = active;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Predmet() {
		
	}

	public Integer getIdPredmet() {
		return idPredmet;
	}

	public void setIdPredmet(Integer idPredmet) {
		this.idPredmet = idPredmet;
	}

	public String getNazivPredmet() {
		return nazivPredmet;
	}

	public void setNazivPredmet(String nazivPredmet) {
		this.nazivPredmet = nazivPredmet;
	}
	@Override
	public String toString() {
		return "Predmet [idPredmet=" + idPredmet + ", nazivPredmet=" + nazivPredmet + ", active=" + active + "]";
	}	
}
