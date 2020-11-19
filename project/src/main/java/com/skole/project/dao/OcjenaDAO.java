package com.skole.project.dao;

import java.util.List;

import com.skole.project.entity.Ocjena;
import com.skole.project.entity.ReportCard;

public interface OcjenaDAO {

	List<Ocjena> getAllOcjene();
	
	List<Ocjena> getOcjenaByIdOsoba(Integer id);
	
	List<Ocjena> getOcjenaByIdPredmet(Integer id);
	
	Ocjena getOcjenaById(Integer id);
	
	boolean createOcjena(Ocjena ocjena);
	
	boolean deleteOcjena(Integer id);
	
	boolean updateOcjena(Ocjena ocjena);
	
	ReportCard getReportCard(Integer id);
}
