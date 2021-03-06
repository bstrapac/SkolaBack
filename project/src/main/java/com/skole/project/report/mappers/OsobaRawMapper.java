package com.skole.project.report.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.report.entity.OsobaRaw;

public class OsobaRawMapper implements RowMapper<OsobaRaw> {
	
	public OsobaRaw mapRow(ResultSet resultSet, int i) throws SQLException {
		
		OsobaRaw osoba = new OsobaRaw();
		osoba.setIdOsoba(resultSet.getInt("idosoba"));
		osoba.setOib(resultSet.getString("oib"));
		osoba.setIme(resultSet.getString("ime"));
		osoba.setPrezime(resultSet.getString("prezime"));
		osoba.setDob(resultSet.getString("dob"));
		osoba.setKontakt(resultSet.getString("kontakt"));
		osoba.setMail(resultSet.getString("mail"));
		osoba.setAdresa(resultSet.getString("adresa"));
		osoba.setIdTipOsobe(resultSet.getInt("idtiposobe"));
		osoba.setActive(resultSet.getBoolean("active"));
		return osoba;
	}
}
