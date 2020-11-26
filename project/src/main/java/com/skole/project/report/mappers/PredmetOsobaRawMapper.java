package com.skole.project.report.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.report.entity.PredmetOsobaRaw;

public class PredmetOsobaRawMapper implements RowMapper<PredmetOsobaRaw> {
	
	@Override
	public PredmetOsobaRaw mapRow(ResultSet rs, int rowNum) throws SQLException {
		PredmetOsobaRaw predmetOsoba = new PredmetOsobaRaw();
		predmetOsoba.setIdPredmetOsoba(rs.getInt("id_predmet_osoba"));
		predmetOsoba.setIdOsoba(rs.getInt("id_osoba"));
		predmetOsoba.setIdPredmet(rs.getInt("id_predmet"));
		return predmetOsoba;
	}
	
}
