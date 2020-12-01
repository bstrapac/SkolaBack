package com.skole.project.predmetosoba;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.entity.PredmetOsoba;

public class PredmetOsobaMapper implements RowMapper<PredmetOsoba> {
	
	public PredmetOsoba mapRow(ResultSet resultSet, int i) throws SQLException{
		
		PredmetOsoba predmetOsoba = new PredmetOsoba();
		predmetOsoba.setIdPredmetOsoba(resultSet.getInt("idpredmetosoba"));
		predmetOsoba.setIdOsoba(resultSet.getInt("idosoba"));
		predmetOsoba.setOsoba(resultSet.getString("osoba"));
		predmetOsoba.setIdPredmet(resultSet.getInt("idpredmet"));
		predmetOsoba.setPredmet(resultSet.getString("predmet"));
		predmetOsoba.setActive(resultSet.getBoolean("active"));
		return predmetOsoba;
	}
}
