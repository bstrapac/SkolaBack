package com.skole.project.report.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.skole.project.report.entity.OcjenaRaw;

public class OcjenaRawMapper implements RowMapper<OcjenaRaw> {

	public OcjenaRaw mapRow(ResultSet resultSet, int i) throws SQLException{
		
		OcjenaRaw ocjena = new OcjenaRaw();
		ocjena.setId(resultSet.getInt("idocjena"));
		ocjena.setIdPredmetOsoba(resultSet.getInt("idpredmetosoba"));
		ocjena.setOcjena(resultSet.getInt("ocjena"));
		ocjena.setDatum(resultSet.getString("datum"));
		ocjena.setIdOsobaDod(resultSet.getInt("idosobadod"));
		return ocjena;
	}
}
