package com.skole.project.entity;

public class Predmet {
	private Integer idPredmet;
	private String nazivPredmet;
	
	public Predmet(Integer idPredmet, String nazivPredmet) {
		super();
		this.idPredmet = idPredmet;
		this.nazivPredmet = nazivPredmet;
	};
	
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
		return "Predmet [idPredmet=" + idPredmet + ", nazivPredmet=" + nazivPredmet + "]";
	};
	
}
