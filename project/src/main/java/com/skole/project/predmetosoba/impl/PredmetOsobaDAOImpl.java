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
				+ " po.id_predmet_osoba,"
				+ " po.id_osoba,"
				+ " o.ime ||' '|| o.prezime as osoba,"
				+ " po.id_predmet,"
				+ " p.naziv_predmet predmet,"
				+ " tip.naziv"
			+ " from predmet_osoba po"
			+ " left join osoba o on"
				+ " po.id_osoba = o.id_osoba"
			+ " left join predmeti p on"
				+ " po.id_predmet = p.id_predmet"
			+ " left join tipovi_osoba tip on"
				+ " o.id_tip_osobe = tip.id_tip_osobe;";
		
		predmetOsoba = jdbcTemplate.query(SQL_GET_ALL, new PredmetOsobaMapper());
		return predmetOsoba;
	}
	
	@Override
	public List<PredmetOsoba> getAllPredmetOsobaByIdOsoba(Integer id) {
		List<PredmetOsoba> list = null;
		
		final String SQL_BY_ID_OSOBA = "select"
				+" po.id_predmet_osoba,"
				+" po.id_osoba,"
				+" o.ime ||' '|| o.prezime as osoba,"
				+" po.id_predmet,"
				+" p.naziv_predmet predmet,"
				+" tip.naziv"
				+" from predmet_osoba po"
				+" left join osoba o on"
					+" po.id_osoba = o.id_osoba"
				+" left join predmeti p on"
					+" po.id_predmet = p.id_predmet"
				+" left join tipovi_osoba tip on"
					+" o.id_tip_osobe = tip.id_tip_osobe" 
				+" where po.id_osoba = ? ";
		
		list = jdbcTemplate.query(SQL_BY_ID_OSOBA, new Object[] {id}, new PredmetOsobaMapper());
		return list;
	}

	@Override
	public List<PredmetOsoba> getAllPredmetOsobaByIdPredmet(Integer id) {
		List<PredmetOsoba> list = null;
		
		final String SQL_BY_ID_OSOBA = "select"
				+" po.id_predmet_osoba,"
				+" po.id_osoba,"
				+" o.ime ||' '|| o.prezime as osoba,"
				+" po.id_predmet,"
				+" p.naziv_predmet predmet,"
				+" tip.naziv"
				+" from predmet_osoba po"
				+" left join osoba o on"
					+" po.id_osoba = o.id_osoba"
				+" left join predmeti p on"
					+" po.id_predmet = p.id_predmet"
				+" left join tipovi_osoba tip on"
					+" o.id_tip_osobe = tip.id_tip_osobe" 
				+" where po.id_predmet = ? ";
		
		list = jdbcTemplate.query(SQL_BY_ID_OSOBA, new Object[] {id}, new PredmetOsobaMapper());
		return list;
	}
	
	
	@Override
	public PredmetOsoba getPredmetOsobaById(Integer id) {
		PredmetOsoba predmetOsoba = null;
		
		final String SQL_GET_BY_ID = "select"
					+ " po.id_predmet_osoba,"
					+ " po.id_osoba,"
					+ " o.ime ||' '|| o.prezime as osoba,"
					+ " po.id_predmet,"
					+ " p.naziv_predmet predmet,"
					+ " tip.naziv"
				+ " from predmet_osoba po"
				+ " left join osoba o on"
					+ " po.id_osoba = o.id_osoba"
				+ " left join predmeti p on"
					+ " po.id_predmet = p.id_predmet"
				+ " left join tipovi_osoba tip on"
					+ " o.id_tip_osobe = tip.id_tip_osobe"
				+ " where id_predmet_osoba = ?;";
		predmetOsoba = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] {id}, new PredmetOsobaMapper());
		return predmetOsoba;
	}
	
	@Override
	public boolean createPredmetOsoba(PredmetOsoba predmetOsoba) {
		
		final String SQL_INSERT = "insert into predmet_osoba ("
					+ "id_osoba, "
					+ "id_predmet) "
				+ "values ( ?, ? )";
		
		return jdbcTemplate.update(SQL_INSERT, predmetOsoba.getIdOsoba(), predmetOsoba.getIdPredmet()) > 0;
	}

	@Override
	public boolean deletePredmetOsoba(Integer id) {
		
		final String SQL_DELETE = "delete from predmet_osoba where id_predmet_osoba = ?";
		
		return jdbcTemplate.update(SQL_DELETE, id) > 0;
	}

	@Override
	public boolean updatePredmetOsoba(PredmetOsoba predmetOsoba) {
		
		final String SQL_UPDATE = "update predmet_osoba set "
					+ "id_osoba = ? ,"
					+ "id_predmet = ? "
				+ "where id_predmet_osoba = ?";
		
		return jdbcTemplate.update(SQL_UPDATE, predmetOsoba.getIdOsoba(), predmetOsoba.getIdPredmet(), predmetOsoba.getIdPredmetOsoba()) > 0;
	}

	@Override
	public Integer getExisting(Integer idOsoba, Integer idPredmet) {
		Integer idZapisa =  null;
		final String SQL_GET = "select "
				+ "id_predmet_osoba "
			+ "from predmet_osoba  "
			+ "where "
				+ "id_osoba =? "
				+ "and id_predmet = ?;";
		
		idZapisa = jdbcTemplate.queryForObject(SQL_GET, new Object[] {idOsoba, idPredmet}, Integer.class);
		return idZapisa;
	}

	@Override
	public Integer createAndGet(Integer idOsoba, Integer idPredmet) {
		
		final String SQL_CREATE = "insert into predmet_osoba ("
					+ "id_osoba, "
					+ "id_predmet) "
				+ "values ( ?,? ); ";
		final String SQL_GET ="select "
					+ "id_predmet_osoba "
				+ "from predmet_osoba "
				+ "where "
					+ "id_osoba = ? and "
					+ "id_predmet = ?";
		jdbcTemplate.update(SQL_CREATE, idOsoba, idPredmet);
		return jdbcTemplate.queryForObject(SQL_GET, new Object[] {idOsoba, idPredmet}, Integer.class);
	}
}










