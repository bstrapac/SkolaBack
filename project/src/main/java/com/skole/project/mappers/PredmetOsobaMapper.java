package com.skole.project.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.entity.PredmetOsoba;

public class PredmetOsobaMapper implements RowMapper<PredmetOsoba> {
	
	public PredmetOsoba mapRow(ResultSet resultSet, int i) throws SQLException{
		
		PredmetOsoba predmetOsoba = new PredmetOsoba();
		predmetOsoba.setIdPredmetOsoba(resultSet.getInt("id_predmet_osoba"));
		predmetOsoba.setIdOsoba(resultSet.getInt("id_osoba"));
		predmetOsoba.setOsoba(resultSet.getString("osoba"));
		predmetOsoba.setIdPredmet(resultSet.getInt("id_predmet"));
		predmetOsoba.setPredmet(resultSet.getString("predmet"));
		return predmetOsoba;
	}
}
