package com.skole.project.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.skole.project.entity.Osoba;
import com.skole.project.mappers.OsobaMapper;

@Repository
public class OsobaDAOImpl implements OsobaDAO {

	@Autowired
	@Qualifier(value="skolaJdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Osoba> getAllOsobe() {
		List<Osoba> osobe = null;

		final String SQL_GET_ALL = "select"
					+ "	o.id_osoba,"
					+ "	o.ime, "
					+ " o.prezime,"
					+ "	o.oib, "
					+ " o.dob,"
					+ "	o.kontakt,"
					+ "	o.adresa,"
					+ "	o.mail, "
					+ "o.id_tip_osobe,"
					+ "	t.naziv as tip"
				+ " from osoba o"
				+ " left join tipovi_osoba t on"
					+ "	o.id_tip_osobe = t.id_tip_osobe;";
		
		osobe = jdbcTemplate.query(SQL_GET_ALL, new OsobaMapper());
		return osobe;
	}
	
	@Override
	public List<Osoba> getAllNastavnici() {
		List<Osoba> osobe = null;
		
		final String SQL_GET_ALL_NASTAVNICI = "select"
				+ "	o.id_osoba,"
				+ "	o.ime, "
				+ " o.prezime,"
				+ "	o.oib, "
				+ " o.dob,"
				+ "	o.kontakt,"
				+ "	o.adresa,"
				+ "	o.mail, "
				+ "o.id_tip_osobe,"
				+ "	t.naziv as tip"
			+ " from osoba o"
			+ " left join tipovi_osoba t on"
				+ "	o.id_tip_osobe = t.id_tip_osobe"
			+ " where o.id_tip_osobe = 1;";
		
		osobe = jdbcTemplate.query(SQL_GET_ALL_NASTAVNICI, new OsobaMapper());
		return osobe;
	}

	@Override
	public List<Osoba> getAllUcenici() {
		List<Osoba> osobe = null;

		final String SQL_GET_ALL_UCENICI = "select"
					+ "	o.id_osoba,"
					+ "	o.ime, "
					+ " o.prezime,"
					+ "	o.oib, "
					+ " o.dob,"
					+ "	o.kontakt,"
					+ "	o.adresa,"
					+ "	o.mail, "
					+ "o.id_tip_osobe,"
					+ "	t.naziv as tip"
				+ " from osoba o"
				+ " left join tipovi_osoba t on"
					+ "	o.id_tip_osobe = t.id_tip_osobe "
				+ " where o.id_tip_osobe = 2;";
		
		osobe = jdbcTemplate.query(SQL_GET_ALL_UCENICI, new OsobaMapper());
		return osobe;
	}

	@Override
	public Osoba getOsobaByID(Integer id) {
		Osoba osoba = null;
		
		final String SQL_GET_BY_ID = "select "
				+ "	o.id_osoba,"
				+ "	o.ime, "
				+ " o.prezime,"
				+ "	o.oib, "
				+ " o.dob,"
				+ "	o.kontakt,"
				+ "	o.adresa,"
				+ "	o.mail, "
				+ "o.id_tip_osobe,"
				+ "	t.naziv as tip"
			+ " from osoba o"
			+ " left join tipovi_osoba t on"
				+ "	o.id_tip_osobe = t.id_tip_osobe"
			+ " where id_osoba = ?";
		
		osoba = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] {id}, new OsobaMapper());
		return osoba;
		}

	@Override
	public boolean createOsoba(Osoba osoba) {
		LocalDate date = LocalDate.parse(osoba.getDob());

		final String SQL_INSERT = "insert into osoba ("
					+ " oib,"
					+ " ime,"
					+ " prezime,"
					+ " dob,"
					+ " kontakt,"
					+ " mail,"
					+ " adresa,"
					+ " id_tip_osobe)"
				+ " values (  ?, ?, ?, ?, ?, ?, ?, ?)";
		
		return jdbcTemplate.update(SQL_INSERT, 
					osoba.getOib(), 
					osoba.getIme(), 
					osoba.getPrezime(), 
					date,
					osoba.getKontakt(), 
					osoba.getMail(), 
					osoba.getAdresa(), 
					osoba.getIdTipOsobe()) > 0;
	}

	@Override
	public boolean deleteOsoba(Integer id) {
		
		final String SQL_DELETE = "delete from osoba where id_osoba = ?";
		
		return jdbcTemplate.update(SQL_DELETE, id) > 0;
	}

	@Override
	public boolean updateOsoba(Osoba osoba) {
		LocalDate date = LocalDate.parse(osoba.getDob());
		
		final String SQL_UPDATE = "update osoba set "
				+ "oib = ?, "
				+ "ime = ?, "
				+ "prezime = ?, "
				+ "dob = ?, "
				+ "kontakt = ?, "
				+ "mail = ?, "
				+ "adresa = ?"
			+ " where id_osoba = ?";
		
		return jdbcTemplate.update(SQL_UPDATE, 
				osoba.getOib(), 
				osoba.getIme(), 
				osoba.getPrezime(), 
				date, 
				osoba.getKontakt(),
				osoba.getMail(), 
				osoba.getAdresa(),
				osoba.getIdOsoba()) > 0;
	}
}
