package com.skole.project.entity;

public class PredmetOsoba {
	public Integer idPredmetOsoba;
	public Integer idOsoba;
	public String osoba;
	public Integer idPredmet;
	public String predmet;
	public Boolean active;
	
	public PredmetOsoba(Integer idPredmetOsoba, Integer idOsoba, String osoba, Integer idPredmet, String predmet, Boolean active) {
		super();
		this.idPredmetOsoba = idPredmetOsoba;
		this.idOsoba = idOsoba;
		this.osoba = osoba;
		this.idPredmet = idPredmet;
		this.predmet = predmet;
		this.active = active;
	}
	public PredmetOsoba() {
		//prazan konstruktor
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
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
	public String getOsoba() {
		return osoba;
	}
	public void setOsoba(String osoba) {
		this.osoba = osoba;
	}
	public Integer getIdPredmet() {
		return idPredmet;
	}
	public void setIdPredmet(Integer idPredmet) {
		this.idPredmet = idPredmet;
	}
	public String getPredmet() {
		return predmet;
	}
	public void setPredmet(String predmet) {
		this.predmet = predmet;
	}
	@Override
	public String toString() {
		return "PredmetOsoba [idPredmetOsoba=" + idPredmetOsoba + ", idOsoba=" + idOsoba + ", osoba=" + osoba
				+ ", idPredmet=" + idPredmet + ", predmet=" + predmet + ", active=" + active + "]";
	}
}
