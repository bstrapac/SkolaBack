package com.skole.project.report.entity;

public class OsobaRaw {
	Integer idOsoba;
	String oib;
	String ime;
	String prezime;
	String dob;
	String kontakt;
	String mail;
	String adresa;
	Integer idTipOsobe;
	
	public OsobaRaw(Integer idOsoba, String oib, String ime, String prezime, String dob, String kontakt, String mail,
			String adresa, Integer idTipOsobe) {
		super();
		this.idOsoba = idOsoba;
		this.oib = oib;
		this.ime = ime;
		this.prezime = prezime;
		this.dob = dob;
		this.kontakt = kontakt;
		this.mail = mail;
		this.adresa = adresa;
		this.idTipOsobe = idTipOsobe;
	}
	
	public OsobaRaw() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIdOsoba() {
		return idOsoba;
	}
	public void setIdOsoba(Integer idOsoba) {
		this.idOsoba = idOsoba;
	}
	public String getOib() {
		return oib;
	}
	public void setOib(String oib) {
		this.oib = oib;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getKontakt() {
		return kontakt;
	}
	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public Integer getIdTipOsobe() {
		return idTipOsobe;
	}
	public void setIdTipOsobe(Integer idTipOsobe) {
		this.idTipOsobe = idTipOsobe;
	}
	
}
