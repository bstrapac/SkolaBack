package com.skole.project.ocjena;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.entity.Ocjena;

public class OcjenaMapper implements RowMapper<Ocjena> {

	public Ocjena mapRow(ResultSet resultSet, int i) throws SQLException{
		
		Ocjena ocjena = new Ocjena();
		ocjena.setIdOcjena(resultSet.getInt("idocjena"));
		ocjena.setIdPredmetOsoba(resultSet.getInt("idpredmetosoba"));
		ocjena.setIdOsoba(resultSet.getInt("idosoba"));
		ocjena.setUcenik(resultSet.getString("ucenik"));
		ocjena.setIdPredmet(resultSet.getInt("idpredmet"));
		ocjena.setPredmet(resultSet.getString("predmet"));
		ocjena.setOcjena(resultSet.getInt("ocjena"));
		ocjena.setDatum(resultSet.getString("datum"));
		ocjena.setIdOsobaDod(resultSet.getInt("idosobadod"));
		ocjena.setNastavnik(resultSet.getString("nastavnik"));
		ocjena.setActive(resultSet.getBoolean("active"));
		return ocjena;
	}
}
