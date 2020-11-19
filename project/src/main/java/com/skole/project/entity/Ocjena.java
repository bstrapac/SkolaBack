package com.skole.project.entity;

public class Ocjena {
	public Integer idOcjena;
	public Integer idPredmetOsoba;
	public Integer idOsoba;
	public String ucenik;
	public Integer idPredmet;
	public String predmet;
	public Integer ocjena;
	public String datum;
	public Integer idOsobaDod;
	public String nastavnik;
	
	public Ocjena(Integer idOcjena, Integer idPredmetOsoba, Integer idOsoba, String ucenik, Integer idPredmet, String predmet, Integer ocjena, String datum, Integer idOsobaDod, String nastavnik) {
		super();
		this.idOcjena = idOcjena;
		this.idPredmetOsoba = idPredmetOsoba;
		this.idOsoba = idOsoba;
		this.ucenik = ucenik;
		this.idPredmet = idPredmet;
		this.predmet = predmet;
		this.ocjena = ocjena;
		this.datum = datum;
		this.idOsobaDod = idOsobaDod;
		this.nastavnik = nastavnik;
	}

	public Integer getIdOcjena() {
		return idOcjena;
	}

	public void setIdOcjena(Integer idOcjena) {
		this.idOcjena = idOcjena;
	}

	public Ocjena() {
		
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
	public Integer getIdPredmetOsoba() {
		return idPredmetOsoba;
	}
	public void setIdPredmetOsoba(Integer idPredmetOsoba) {
		this.idPredmetOsoba = idPredmetOsoba;
	}
	public String getUcenik() {
		return ucenik;
	}
	public void setUcenik(String ucenik) {
		this.ucenik = ucenik;
	}
	public String getPredmet() {
		return predmet;
	}
	public void setPredmet(String predmet) {
		this.predmet = predmet;
	}
	public Integer getOcjena() {
		return ocjena;
	}
	public void setOcjena(Integer ocjena) {
		this.ocjena = ocjena;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public Integer getIdOsobaDod() {
		return idOsobaDod;
	}
	public void setIdOsobaDod(Integer idOsobaDod) {
		this.idOsobaDod = idOsobaDod;
	}
	public String getNastavnik() {
		return nastavnik;
	}
	public void setNastavnik(String nastavnik) {
		this.nastavnik = nastavnik;
	}
	
	
	@Override
	public String toString() {
		return "Ocjena [idPredmetOsoba=" + idPredmetOsoba + ", idOsoba=" + idOsoba + ", ucenik=" + ucenik
				+ ", idPredmet=" + idPredmet + ", predmet=" + predmet + ", ocjena=" + ocjena + ", datum=" + datum
				+ ", idOsobaDod=" + idOsobaDod + ", nastavnik=" + nastavnik + "]";
	}
}
