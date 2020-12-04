package com.skole.project.predmetosoba.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.skole.project.entity.PredmetOsoba;
import com.skole.project.predmetosoba.PredmetOsobaDAO;
import com.skole.project.predmetosoba.PredmetOsobaMapper;

@Repository
public class PredmetOsobaDAOImpl implements PredmetOsobaDAO {
	
	@Autowired
	@Qualifier(value="skolaJdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Override
	public List<PredmetOsoba> getAllPredmetOsoba() {
		List<PredmetOsoba> predmetOsoba = null;
		
		final String SQL_GET_ALL = "select"
				+ "	po.idpredmetosoba,"
				+ "	po.idpredmet,"
				+ "	p.nazivpredmt predmet,"
				+ "	po.idosoba,"
				+ "	o.ime || ' ' || o.prezime osoba,"
				+ "	po.active,"
				+ "	t.naziv"
				+ " from skola.predmetosoba po"
				+ " left join skola.osobe o on"
				+ "	po.idosoba = o.idosoba"
				+ " left join skola.predmeti p on"
				+ "	po.idpredmet = p.idpredmet"
				+ " left join skola.tipoviosoba t on"
				+ "	o.idtiposobe = t.idtiposoba ";
		
		predmetOsoba = jdbcTemplate.query(SQL_GET_ALL, new PredmetOsobaMapper());
		return predmetOsoba;
	}
	
	@Override
	public List<PredmetOsoba> getAllPredmetOsobaByIdOsoba(Integer id) {
		List<PredmetOsoba> list = null;
		
		final String SQL_BY_ID_OSOBA = "select"
				+ "	po.idpredmetosoba,"
				+ "	po.idpredmet,"
				+ "	p.nazivpredmt predmet,"
				+ "	po.idosoba,"
				+ "	o.ime || ' ' || o.prezime osoba,"
				+ "	po.active,"
				+ "	t.naziv"
				+ " from skola.predmetosoba po"
				+ " left join skola.osobe o on"
				+ "	po.idosoba = o.idosoba"
				+ " left join skola.predmeti p on"
				+ "	po.idpredmet = p.idpredmet"
				+ " left join skola.tipoviosoba t on"
				+ "	o.idtiposobe = t.idtiposoba "
				+ " where po.idosoba = ? ";
		
		list = jdbcTemplate.query(SQL_BY_ID_OSOBA, new Object[] {id}, new PredmetOsobaMapper());
		return list;
	}

	@Override
	public List<PredmetOsoba> getAllPredmetOsobaByIdPredmet(Integer id) {
		List<PredmetOsoba> list = null;
		
		final String SQL_BY_ID_OSOBA = "select"
				+ "	po.idpredmetosoba,"
				+ "	po.idpredmet,"
				+ "	p.nazivpredmt predmet,"
				+ "	po.idosoba,"
				+ "	o.ime || ' ' || o.prezime osoba,"
				+ "	po.active,"
				+ "	t.naziv"
				+ " from skola.predmetosoba po"
				+ " left join skola.osobe o on"
				+ "	po.idosoba = o.idosoba"
				+ " left join skola.predmeti p on"
				+ "	po.idpredmet = p.idpredmet"
				+ " left join skola.tipoviosoba t on"
				+ "	o.idtiposobe = t.idtiposoba "
				+ " where po.idpredmet = ? ";
		
		list = jdbcTemplate.query(SQL_BY_ID_OSOBA, new Object[] {id}, new PredmetOsobaMapper());
		return list;
	}
	
	
	@Override
	public PredmetOsoba getPredmetOsobaById(Integer id) {
		PredmetOsoba predmetOsoba = null;
		
		final String SQL_GET_BY_ID = "select"
				+ "	po.idpredmetosoba,"
				+ "	po.idpredmet,"
				+ "	p.nazivpredmt predmet,"
				+ "	po.idosoba,"
				+ "	o.ime || ' ' || o.prezime osoba,"
				+ "	po.active,"
				+ "	t.naziv"
				+ " from skola.predmetosoba po"
				+ " left join skola.osobe o on"
				+ "	po.idosoba = o.idosoba"
				+ " left join skola.predmeti p on"
				+ "	po.idpredmet = p.idpredmet"
				+ " left join skola.tipoviosoba t on"
				+ "	o.idtiposobe = t.idtiposoba "
				+ " where idpredmetosoba = ?;";
		predmetOsoba = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] {id}, new PredmetOsobaMapper());
		return predmetOsoba;
	}
	
	@Override
	public boolean createPredmetOsoba(PredmetOsoba predmetOsoba) {
		
		final String SQL_INSERT = "insert into predmetosoba ("
				+ "idosoba, "
				+ "idpredmet) "
				+ "values ( ?, ? )";
		
		return jdbcTemplate.update(SQL_INSERT, predmetOsoba.getIdOsoba(), predmetOsoba.getIdPredmet()) > 0;
	}

	@Override
	public boolean deletePredmetOsoba(Integer id) {
		
		final String SQL_DELETE = "delete"
				+ " from predmetosoba"
				+ " where idpredmetosoba = ?";
		
		return jdbcTemplate.update(SQL_DELETE, id) > 0;
	}

	@Override
	public Integer getExisting(Integer idOsoba, Integer idPredmet) {
		Integer idZapisa =  null;
		final String SQL_GET = "select"
				+ " idpredmetosoba"
				+ " from predmetosoba"
				+ " where"
				+ " idosoba = ?"
				+ " and idpredmet = ?;";
		
		idZapisa = jdbcTemplate.queryForObject(SQL_GET, new Object[] {idOsoba, idPredmet}, Integer.class);
		return idZapisa;
	}

	@Override
	public Integer createAndGet(Integer idOsoba, Integer idPredmet) {
		
		final String SQL_CREATE = "insert into predmetosoba ("
				+ "idosoba, "
				+ "idpredmet) "
				+ "values ( ?,? ); ";
		
		final String SQL_GET ="select"
				+ " idpredmetosoba"
				+ " from predmetosoba"
				+ " where"
				+ " idosoba = ? and"
				+ " idpredmet = ?";
		jdbcTemplate.update(SQL_CREATE, idOsoba, idPredmet);
		return jdbcTemplate.queryForObject(SQL_GET, new Object[] {idOsoba, idPredmet}, Integer.class);
	}
}










