package com.skole.project.report.entity;

public class PredmetOsobaRaw {
	
	Integer idPredmetOsoba;
	Integer idOsoba;
	Integer idPredmet;
	
	public PredmetOsobaRaw() {
	}
	
	public PredmetOsobaRaw(Integer idPredmetOsoba, Integer idOsoba, Integer idPredmet) {
		super();
		this.idPredmetOsoba = idPredmetOsoba;
		this.idOsoba = idOsoba;
		this.idPredmet = idPredmet;
	}
	
	public Integer getIdPredmetOsoba() {
		return idPredmetOsoba;
	}
	public void setIdPredmetOsoba(Integer idPredmetOsoba) {
		this.idPredmetOsoba = idPredmetOsoba;
	}
	public Integer getIdOsoba() {
		return idOsoba;
	}
	public void setIdOsoba(Integer idOsoba) {
		this.idOsoba = idOsoba;
	}
	public Integer getIdPredmet() {
		return idPredmet;
	}
	public void setIdPredmet(Integer idPredmet) {
		this.idPredmet = idPredmet;
	}
	
	

}
