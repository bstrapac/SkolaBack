package com.skole.project.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.entity.Osoba;

public class OsobaMapper implements RowMapper<Osoba>{
	
	public Osoba mapRow(ResultSet resultSet, int i) throws SQLException{
		
		Osoba osoba = new Osoba();
		osoba.setIdOsoba(resultSet.getInt("id_osoba"));
		osoba.setOib(resultSet.getString("oib"));
		osoba.setIme(resultSet.getString("ime"));
		osoba.setPrezime(resultSet.getString("prezime"));
		osoba.setDob(resultSet.getString("dob"));
		osoba.setKontakt(resultSet.getString("kontakt"));
		osoba.setMail(resultSet.getString("mail"));
		osoba.setAdresa(resultSet.getString("adresa"));
		osoba.setIdTipOsobe(resultSet.getInt("id_tip_osobe"));
		osoba.setTip(resultSet.getString("tip"));
		return osoba;
	}
}
