package com.skole.project.report.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.report.entity.PredmetOsobaRaw;

public class PredmetOsobaRawMapper implements RowMapper<PredmetOsobaRaw> {
	
	@Override
	public PredmetOsobaRaw mapRow(ResultSet rs, int rowNum) throws SQLException {
		PredmetOsobaRaw predmetOsoba = new PredmetOsobaRaw();
		predmetOsoba.setIdPredmetOsoba(rs.getInt("idpredmetosoba"));
		predmetOsoba.setIdOsoba(rs.getInt("idosoba"));
		predmetOsoba.setIdPredmet(rs.getInt("idpredmet"));
		predmetOsoba.setActive(rs.getBoolean("active"));
		return predmetOsoba;
	}
	
}
