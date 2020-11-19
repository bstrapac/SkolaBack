package com.skole.project.dao;

import java.util.List;

import com.skole.project.entity.Osoba;

public interface OsobaDAO {
	List<Osoba> getAllOsobe();
	
	List<Osoba> getAllNastavnici();
	
	List<Osoba> getAllUcenici();
	
	Osoba getOsobaByID(Integer id);
	
	boolean createOsoba(Osoba osoba);
	
	boolean deleteOsoba(Integer id);
	
	boolean updateOsoba(Osoba osoba);

}
