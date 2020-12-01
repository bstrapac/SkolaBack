package com.skole.project.osoba.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.skole.project.entity.Osoba;
import com.skole.project.osoba.OsobaDAO;
import com.skole.project.osoba.OsobaMapper;

@Repository
public class OsobaDAOImpl implements OsobaDAO {

	@Autowired
	@Qualifier(value="skolaJdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Osoba> getAllOsobe() {
		List<Osoba> osobe = null;

		final String SQL_GET_ALL = "select"
				+ "	o.idosoba,"
				+ "	o.ime,"
				+ "	o.prezime,"
				+ "	o.dob,"
				+ "	o.oib,"
				+ "	attr.adresa,"
				+ "	attr.mail,"
				+ "	attr.kontakt,"
				+ "	o.idtiposobe,"
				+ "	t.naziv as tip"
				+ " from skola.osobe o"
				+ " left join skola.osobaattr attr on"
				+ "	o.idosoba = attr.idosoba"
				+ " left join skola.tipoviosoba t"
				+ " on o.idtiposobe = t.idtiposoba;";
		
		osobe = jdbcTemplate.query(SQL_GET_ALL, new OsobaMapper());
		return osobe;
	}
	
	@Override
	public List<Osoba> getAllNastavnici() {
		List<Osoba> osobe = null;
		
		final String SQL_GET_ALL_NASTAVNICI = "select"
				+ "	o.idosoba,"
				+ "	o.ime,"
				+ "	o.prezime,"
				+ "	o.dob,"
				+ "	o.oib,"
				+ "	attr.adresa,"
				+ "	attr.mail,"
				+ "	attr.kontakt,"
				+ "	o.idtiposobe,"
				+ "	t.naziv as tip"
				+ " from skola.osobe o"
				+ " left join skola.osobaattr attr on"
				+ "	o.idosoba = attr.idosoba"
				+ " left join skola.tipoviosoba t"
				+ " on o.idtiposobe = t.idtiposoba"
				+ " where o.idtiposobe = 2;";
		
		osobe = jdbcTemplate.query(SQL_GET_ALL_NASTAVNICI, new OsobaMapper());
		return osobe;
	}

	@Override
	public List<Osoba> getAllUcenici() {
		List<Osoba> osobe = null;

		final String SQL_GET_ALL_UCENICI = "select"
				+ "	o.idosoba,"
				+ "	o.ime,"
				+ "	o.prezime,"
				+ "	o.dob,"
				+ "	o.oib,"
				+ "	attr.adresa,"
				+ "	attr.mail,"
				+ "	attr.kontakt,"
				+ "	o.idtiposobe,"
				+ "	t.naziv as tip"
				+ " from skola.osobe o"
				+ " left join skola.osobaattr attr on"
				+ "	o.idosoba = attr.idosoba"
				+ " left join skola.tipoviosoba t"
				+ " on o.idtiposobe = t.idtiposoba"
				+ " where o.idtiposobe = 3;";
		
		osobe = jdbcTemplate.query(SQL_GET_ALL_UCENICI, new OsobaMapper());
		return osobe;
	}

	@Override
	public Osoba getOsobaByID(Integer id) {
		Osoba osoba = null;
		
		final String SQL_GET_BY_ID = "select"
				+ "	o.idosoba,"
				+ "	o.ime,"
				+ "	o.prezime,"
				+ "	o.dob,"
				+ "	o.oib,"
				+ "	attr.adresa,"
				+ "	attr.mail,"
				+ "	attr.kontakt,"
				+ "	o.idtiposobe,"
				+ "	t.naziv as tip"
				+ " from skola.osobe o"
				+ " left join skola.osobaattr attr on"
				+ "	o.idosoba = attr.idosoba"
				+ " left join skola.tipoviosoba t"
				+ " on o.idtiposobe = t.idtiposoba"
				+ " where o.idosoba = ?;";
		
		osoba = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] {id}, new OsobaMapper());
		return osoba;
		}

	@Override
	public boolean createOsoba(Osoba osoba) {
		LocalDate date = LocalDate.parse(osoba.getDob());
		boolean stat = false;

		final String SQL_INSERT = "insert into osoba ("
					+ " oib,"
					+ " ime,"
					+ " prezime,"
					+ " dob,"
					+ " idtiposobe)"
				+ " values ( ?, ?, ?, ?, ?)";
		final String SQL_OSOBA_ATTR="UPDATE skola.osobaattr"
				+ " SET mail=?,"
				+ " kontakt=?,"
				+ " adresa=?"
				+ " where idosoba = ?;";
		
		
		stat = jdbcTemplate.update(SQL_INSERT, 
					osoba.getOib(), 
					osoba.getIme(), 
					osoba.getPrezime(), 
					date,
					osoba.getIdTipOsobe()) > 0;
		jdbcTemplate.update(SQL_OSOBA_ATTR, osoba.getMail(), osoba.getKontakt(), osoba.getAdresa());		
		return stat;
	}

	@Override
	public boolean deleteOsoba(Integer id) {
		
		final String SQL_DELETE = "delete from osoba where idosoba = ?";
		
		return jdbcTemplate.update(SQL_DELETE, id) > 0;
	}

	@Override
	public boolean updateOsoba(Osoba osoba) {		
		final String SQL_UPDATE = "update osobaattr set "
				+ " kontakt = ?,"
				+ " mail = ?,"
				+ " adresa = ?"
			+ " where idosoba = ?";
		
		return jdbcTemplate.update(SQL_UPDATE, 
				osoba.getKontakt(),
				osoba.getMail(), 
				osoba.getAdresa(),
				osoba.getIdOsoba()) > 0;
	}
}
