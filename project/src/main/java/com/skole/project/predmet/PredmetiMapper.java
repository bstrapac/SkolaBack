package com.skole.project.predmet;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.entity.Predmet;

public class PredmetiMapper implements RowMapper<Predmet>{

	public Predmet mapRow(ResultSet resultSet, int i) throws SQLException{
		
		Predmet predmet = new Predmet();
		predmet.setIdPredmet(resultSet.getInt("idpredmet"));
		predmet.setNazivPredmet(resultSet.getString("nazivpredmt"));
		predmet.setActive(resultSet.getBoolean("active"));
		return predmet;
	}
}
