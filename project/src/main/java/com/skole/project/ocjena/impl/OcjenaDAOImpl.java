package com.skole.project.ocjena.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.skole.project.entity.Ocjena;
import com.skole.project.ocjena.OcjenaDAO;
import com.skole.project.ocjena.OcjenaMapper;

@Repository
public class OcjenaDAOImpl implements OcjenaDAO {
	
	@Autowired
	@Qualifier(value="skolaJdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Ocjena> getAllOcjene() {
		
		List<Ocjena> ocjene = null;
		
		final String SQL_GET_ALL = "select"
					+ " oc.idocjena,"
					+ "	oc.idpredmetosoba,"
					+ " po.idosoba,"
					+ " po.idpredmet,"
					+ "	o.ime ||' '|| o.prezime as ucenik,"
					+ "	oc.ocjena,"
					+ "	oc.datum,"
					+ "	p.nazivpredmt predmet,"
					+ "	oc.idosobadod,"
					+ "	o2.ime|| ' ' ||o2.prezime as nastavnik,"
					+ " oc.active "
				+ " from ocjene oc"
				+ " left join predmetosoba po on"
					+ "	po.idpredmetosoba = oc.idpredmetosoba"
				+ " left join osobe o on"
					+ "	o.idosoba = po.idosoba"
				+ " left join predmeti p on"
					+ "	p.idpredmet = po.idpredmet"
				+ " left join osobe o2 on"
					+ "	oc.idosobadod = o2.idosoba"
					+ " where oc.active = true"
					+ " order by oc.idocjena asc;";
		try {
			ocjene = jdbcTemplate.query(SQL_GET_ALL, new OcjenaMapper());
		} catch(Exception e) {
			throw e;
		}
		return ocjene;
	}
	
	@Override
	public List<Ocjena> getOcjenaByIdOsoba(Integer id){
		List<Ocjena> ocjene = null;
		final String SQL_GET_BY_idosoba ="select"
				+ " oc.idocjena,"
				+ "	oc.idpredmetosoba,"
				+ "po.idosoba,"
				+ "	o.ime ||' '|| o.prezime as ucenik,"
				+ "	oc.ocjena,"
				+ "	oc.datum,"
				+ " po.idpredmet,"
				+ "	p.nazivpredmt predmet,"
				+ "	oc.idosobadod,"
				+ "	o2.ime|| ' ' ||o2.prezime as nastavnik,"
				+ " oc.active"
			+ " from ocjene oc"
			+ " left join predmetosoba po on"
				+ "	po.idpredmetosoba = oc.idpredmetosoba"
			+ " left join osobe o on"
				+ "	o.idosoba = po.idosoba"
			+ " left join predmeti p on"
				+ "	p.idpredmet = po.idpredmet"
			+ " left join osobe o2 on"
				+ "	oc.idosobadod = o2.idosoba"
			+ " where po.idosoba = ? and oc.active = true;";
		try {
			ocjene = jdbcTemplate.query(SQL_GET_BY_idosoba, new Object[] {id}, new OcjenaMapper() );	
		} catch(Exception e) {
			throw e;
		}
		return ocjene;
	}
	
	@Override
	public List<Ocjena> getOcjenaByIdPredmet(Integer id){
		List<Ocjena> ocjene = null;
		
		final String SQL_GET_BY_idpredmet ="select"
				+ " oc.idocjena,"
				+ "	oc.idpredmetosoba,"
				+ " po.idosoba,"
				+ "	o.ime ||' '|| o.prezime as ucenik,"
				+ "	oc.ocjena,"
				+ "	oc.datum,"
				+ " po.idpredmet,"
				+ "	p.nazivpredmt predmet,"
				+ "	oc.idosobadod,"
				+ "	o2.ime|| ' ' ||o2.prezime as nastavnik,"
				+ " oc.active"
			+ " from ocjene oc"
			+ " left join predmetosoba po on"
				+ "	po.idpredmetosoba = oc.idpredmetosoba"
			+ " left join osobe o on"
				+ "	o.idosoba = po.idosoba"
			+ " left join predmeti p on"
				+ "	p.idpredmet = po.idpredmet"
			+ " left join osobe o2 on"
				+ "	oc.idosobadod = o2.idosoba"
			+ " where po.idpredmet = ? and oc.active =  true;";
		try {
			ocjene = jdbcTemplate.query(SQL_GET_BY_idpredmet, new Object[] {id}, new OcjenaMapper() );
		} catch(Exception e) {
			throw e;
		}
		return ocjene;
	}
	
	@Override
	public Ocjena getOcjenaById(Integer id) {
		Ocjena ocjena = null;
		
		final String SQL_GET_BY_ID ="select"
				+ " oc.idocjena,"
				+ "	oc.idpredmetosoba,"
				+ " o.idosoba,"
				+ "	o.ime ||' '|| o.prezime as ucenik,"
				+ "	oc.ocjena,"
				+ "	oc.datum,"
				+ "	p.idpredmet,"
				+ "	p.nazivpredmt predmet,"
				+ "	oc.idosobadod,"
				+ "	o2.ime|| ' ' ||o2.prezime as nastavnik,"
				+ " oc.active "
			+ " from ocjene oc"
			+ " left join predmetosoba po on"
				+ "	po.idpredmetosoba = oc.idpredmetosoba"
			+ " left join osobe o on"
				+ "	o.idosoba = po.idosoba"
			+ " left join predmeti p on"
				+ "	p.idpredmet = po.idpredmet"
			+ " left join osobe o2 on"
				+ "	oc.idosobadod = o2.idosoba"
			+ " where oc.idocjena = ?;";
		
		try {
			ocjena = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] {id}, new OcjenaMapper());
		} catch(Exception e) {
			throw e;
		}
		return ocjena;
	}

	@Override
	public boolean createOcjena(Ocjena ocjena) {
		LocalDate date = LocalDate.now();
		
		final String SQL_INSERT = "insert into ocjene ("
				+ "idpredmetosoba, "
				+ "ocjena, "
				+ "datum, "
				+ "idosobadod) "
		+ "values ( ?, ?, ?, ? )";
		
		try {
			return jdbcTemplate.update(SQL_INSERT, 
				ocjena.getIdPredmetOsoba(), 
				ocjena.getOcjena(), 
				date, 
				ocjena.getIdOsobaDod()) > 0;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean deleteOcjena(Integer id) {
		final String SQL_DELETE = "delete from ocjene where idocjena = ?";
		try {
			return jdbcTemplate.update(SQL_DELETE, id) > 0 ;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean updateOcjena(Ocjena ocjena) {
		LocalDate date = LocalDate.now();
		final String SQL_UPDATE = "update ocjene set "
					+ "ocjena = ?, "
					+ "datum = ?, "
					+ "idosobadod = ? "
				+ "where idocjena = ?";
		
		try {
			return jdbcTemplate.update(SQL_UPDATE, 
				ocjena.getOcjena(), 
				date, 
				ocjena.getIdOsobaDod(), 
				ocjena.getIdOcjena()) > 0;
		} catch (Exception e) {
			throw e;
		}
	}
}





