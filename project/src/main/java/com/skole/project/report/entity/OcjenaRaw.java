package com.skole.project.report.entity;

public class OcjenaRaw {

	Integer id;
	Integer idPredmetOsoba;
	Integer ocjena;
	String datum;
	Integer idOsobaDod;
	boolean active;
	
	

	public OcjenaRaw(Integer id, Integer idPredmetOsoba, Integer ocjena, String datum, Integer idOsobaDod,
			boolean active) {
		super();
		this.id = id;
		this.idPredmetOsoba = idPredmetOsoba;
		this.ocjena = ocjena;
		this.datum = datum;
		this.idOsobaDod = idOsobaDod;
		this.active = active;
	}

	public OcjenaRaw() {
		// TODO Auto-generated constructor stub
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPredmetOsoba() {
		return idPredmetOsoba;
	}

	public void setIdPredmetOsoba(Integer idPredmetOsoba) {
		this.idPredmetOsoba = idPredmetOsoba;
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
}
