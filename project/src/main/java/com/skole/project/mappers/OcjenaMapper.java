package com.skole.project.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.entity.Ocjena;

public class OcjenaMapper implements RowMapper<Ocjena> {

	public Ocjena mapRow(ResultSet resultSet, int i) throws SQLException{
		
		Ocjena ocjena = new Ocjena();
		ocjena.setIdOcjena(resultSet.getInt("id_ocjena"));
		ocjena.setIdPredmetOsoba(resultSet.getInt("id_predmet_osoba"));
		ocjena.setIdOsoba(resultSet.getInt("id_osoba"));
		ocjena.setUcenik(resultSet.getString("ucenik"));
		ocjena.setIdPredmet(resultSet.getInt("id_predmet"));
		ocjena.setPredmet(resultSet.getString("predmet"));
		ocjena.setOcjena(resultSet.getInt("ocjena"));
		ocjena.setDatum(resultSet.getString("datum"));
		ocjena.setIdOsobaDod(resultSet.getInt("id_osoba_dod"));
		ocjena.setNastavnik(resultSet.getString("nastavnik"));
		return ocjena;
	}
}
