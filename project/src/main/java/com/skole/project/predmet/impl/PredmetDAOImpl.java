package com.skole.project.predmet.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.skole.project.entity.Predmet;
import com.skole.project.predmet.PredmetDAO;
import com.skole.project.predmet.PredmetiMapper;

@Repository
public class PredmetDAOImpl implements PredmetDAO {
	
	@Autowired
	@Qualifier(value="skolaJdbcTemplate")
	JdbcTemplate jdbcTemplate;

	public List<Predmet> getAllPredmeti(){
		List<Predmet> predmeti = null;
		
		final String SQL_GET_ALL = "SELECT * FROM predmeti where active = true order by idpredmet asc;";
		try {
			predmeti =  jdbcTemplate.query(SQL_GET_ALL, new PredmetiMapper());
		} catch(Exception e) {
			throw e;
		}
		
		return predmeti;
	}
	
	@Override
	public Predmet getPredmetById(Integer id) {
		Predmet predmet = null;
		
		final String SQL_GET_BY_ID = "select * from predmeti where idpredmet = ?";
		try {
			predmet = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] { id }, new PredmetiMapper());
		} catch(Exception e) {
			throw e;
		}
		return predmet;
	}
	
	@Override
	public boolean createPredmet(Predmet predmet) {
		final String SQL_INSERT = "insert into predmeti (nazivpredmt) values ( ? )";
		try {
			return jdbcTemplate.update(SQL_INSERT, predmet.getNazivPredmet()) > 0;
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public boolean deletePredmet(Integer id) {
		final String SQL_DELETE = "delete from predmeti where idpredmet = ?";
		try {
			return jdbcTemplate.update(SQL_DELETE, id) > 0;
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public boolean updatePredmet(Predmet predmet) {
		final String SQL_UPDATE = "update predmeti set nazivpredmt = ? where idpredmet = ?";
		try {
			return jdbcTemplate.update(SQL_UPDATE, predmet.getNazivPredmet(), predmet.getIdPredmet()) > 0;
		} catch(Exception e) {
			throw e;
		}
	}
}
