package com.skole.project.dao;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.skole.project.entity.Ocjena;
import com.skole.project.entity.ReportCard;
import com.skole.project.mappers.OcjenaMapper;

@Repository
public class OcjenaDAOImpl implements OcjenaDAO {
	
	@Autowired
	@Qualifier(value="skolaJdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Ocjena> getAllOcjene() {
		
		List<Ocjena> ocjene = null;
		
		final String SQL_GET_ALL = "select"
					+ " oc.id_ocjena,"
					+ "	oc.id_predmet_osoba,"
					+ " po.id_osoba,"
					+ " po.id_predmet,"
					+ "	o.ime ||' '|| o.prezime as ucenik,"
					+ "	oc.ocjena,"
					+ "	oc.datum,"
					+ "	p.naziv_predmet predmet,"
					+ "	oc.id_osoba_dod,"
					+ "	o2.ime|| ' ' ||o2.prezime as nastavnik"
				+ " from ocjene oc"
				+ " left join predmet_osoba po on"
					+ "	po.id_predmet_osoba = oc.id_predmet_osoba"
				+ " left join osoba o on"
					+ "	o.id_osoba = po.id_osoba"
				+ " left join predmeti p on"
					+ "	p.id_predmet = po.id_predmet"
				+ " left join osoba o2 on"
					+ "	oc.id_osoba_dod = o2.id_osoba;";
		
		ocjene = jdbcTemplate.query(SQL_GET_ALL, new OcjenaMapper());
		return ocjene;
	}
	
	@Override
	public List<Ocjena> getOcjenaByIdOsoba(Integer id){
		List<Ocjena> ocjene = null;
		
		final String SQL_GET_BY_ID_OSOBA ="select"
				+ " oc.id_ocjena,"
				+ "	oc.id_predmet_osoba,"
				+ "po.id_osoba,"
				+ "	o.ime ||' '|| o.prezime as ucenik,"
				+ "	oc.ocjena,"
				+ "	oc.datum,"
				+ " po.id_predmet,"
				+ "	p.naziv_predmet predmet,"
				+ "	oc.id_osoba_dod,"
				+ "	o2.ime|| ' ' ||o2.prezime as nastavnik"
			+ " from ocjene oc"
			+ " left join predmet_osoba po on"
				+ "	po.id_predmet_osoba = oc.id_predmet_osoba"
			+ " left join osoba o on"
				+ "	o.id_osoba = po.id_osoba"
			+ " left join predmeti p on"
				+ "	p.id_predmet = po.id_predmet"
			+ " left join osoba o2 on"
				+ "	oc.id_osoba_dod = o2.id_osoba"
			+ " where po.id_osoba = ?;";
		
		ocjene = jdbcTemplate.query(SQL_GET_BY_ID_OSOBA, new Object[] {id}, new OcjenaMapper() );
		return ocjene;
	}
	
	@Override
	public List<Ocjena> getOcjenaByIdPredmet(Integer id){
		List<Ocjena> ocjene = null;
		
		final String SQL_GET_BY_ID_PREDMET ="select"
				+ " oc.id_ocjena,"
				+ "	oc.id_predmet_osoba,"
				+ " po.id_osoba,"
				+ "	o.ime ||' '|| o.prezime as ucenik,"
				+ "	oc.ocjena,"
				+ "	oc.datum,"
				+ " po.id_predmet,"
				+ "	p.naziv_predmet predmet,"
				+ "	oc.id_osoba_dod,"
				+ "	o2.ime|| ' ' ||o2.prezime as nastavnik"
			+ " from ocjene oc"
			+ " left join predmet_osoba po on"
				+ "	po.id_predmet_osoba = oc.id_predmet_osoba"
			+ " left join osoba o on"
				+ "	o.id_osoba = po.id_osoba"
			+ " left join predmeti p on"
				+ "	p.id_predmet = po.id_predmet"
			+ " left join osoba o2 on"
				+ "	oc.id_osoba_dod = o2.id_osoba"
			+ " where po.id_predmet = ?;";
		
		ocjene = jdbcTemplate.query(SQL_GET_BY_ID_PREDMET, new Object[] {id}, new OcjenaMapper() );
		return ocjene;
	}
	
	@Override
	public Ocjena getOcjenaById(Integer id) {
		Ocjena ocjena = null;
		
		final String SQL_GET_BY_ID ="select"
				+ " oc.id_ocjena,"
				+ "	oc.id_predmet_osoba,"
				+ "	o.ime ||' '|| o.prezime as ucenik,"
				+ "	oc.ocjena,"
				+ "	oc.datum,"
				+ "	p.naziv_predmet predmet,"
				+ "	oc.id_osoba_dod,"
				+ "	o2.ime|| ' ' ||o2.prezime as nastavnik"
			+ " from ocjene oc"
			+ " left join predmet_osoba po on"
				+ "	po.id_predmet_osoba = oc.id_predmet_osoba"
			+ " left join osoba o on"
				+ "	o.id_osoba = po.id_osoba"
			+ " left join predmeti p on"
				+ "	p.id_predmet = po.id_predmet"
			+ " left join osoba o2 on"
				+ "	oc.id_osoba_dod = o2.id_osoba"
			+ " where oc.id_ocjena = ?;";
		
		ocjena = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] {id}, new OcjenaMapper());
		return ocjena;
	}

	@Override
	public boolean createOcjena(Ocjena ocjena) {
		LocalDate date = LocalDate.parse(ocjena.getDatum());
		
		final String SQL_INSERT = "insert into ocjene ("
				+ "id_predmet_osoba, "
				+ "ocjena, "
				+ "datum, "
				+ "id_osoba_dod) "
		+ "values ( ?, ?, ?, ? )";
		
		return jdbcTemplate.update(SQL_INSERT, 
				ocjena.getIdPredmetOsoba(), 
				ocjena.getOcjena(), 
				date, 
				ocjena.getIdOsobaDod()) > 0;
	}

	@Override
	public boolean deleteOcjena(Integer id) {
		final String SQL_DELETE = "delete from ocjene where id_ocjena = ?";
		
		return jdbcTemplate.update(SQL_DELETE, id) > 0 ;
	}

	@Override
	public boolean updateOcjena(Ocjena ocjena) {
		LocalDate date = LocalDate.parse(ocjena.getDatum());
		
		final String SQL_UPDATE = "update ocjene set "
					+ "ocjena = ?, "
					+ "datum = ?, "
					+ "id_osoba_dod = ? "
				+ "where id_ocjena = ?";
		
		return jdbcTemplate.update(SQL_UPDATE, 
				ocjena.getOcjena(), 
				date, 
				ocjena.getIdOsobaDod(), 
				ocjena.getIdPredmetOsoba()) > 0;
	}
	
	public ReportCard getReportCard(Integer id) {
		ReportCard reportCard = new ReportCard();
		List<HashMap<String, String>> avg = new ArrayList<>();
		Double finalGrade;
		
		final String SQL_REPORT = "select"
				+ "	avg(oc.ocjena) as avg,"
				+ "	p.naziv_predmet as predmet"
				+ " from ocjene oc"
				+ " left join predmet_osoba po"
				+ " on oc.id_predmet_osoba = po.id_predmet_osoba"
				+ " left join predmeti p"
				+ " on po.id_predmet = p.id_predmet"
				+ " where po.id_osoba = ? "
				+ " group by p.naziv_predmet;";
		
		final String SQL_FINAL_GRADE = "select"
				+ "	avg(ocjena) as final_grade"
				+ " from ("
				+ "	select"
					+ "	avg(oc.ocjena) as ocjena"
					+ "	from ocjene oc"
					+ "	left join predmet_osoba po"
					+ "	on oc.id_predmet_osoba = po.id_predmet_osoba"
					+ "	left join predmeti p"
					+ "	on po.id_predmet = p.id_predmet"
					+ "	where po.id_osoba = ?"
					+ "	group by p.naziv_predmet) tempTable;";
		
		jdbcTemplate.query(SQL_REPORT, new Object [] {id}, (ResultSet rs) -> {
			HashMap<String, String> results = new HashMap<>();
			do {
				results = new HashMap<>();
				//results.put("predmet", rs.getString("predmet"));
				//results.put("prosjek", String.valueOf(rs.getDouble("avg")));
				results.put(rs.getString("predmet"),String.valueOf(rs.getDouble("avg")));
				avg.add(results);
			} while(rs.next());
			
		});
		finalGrade = jdbcTemplate.queryForObject(SQL_FINAL_GRADE, new Object[] {id},  Double.class);
		
		reportCard.setAvgOcjene(avg);
		reportCard.setFinalGrade(finalGrade);

		return reportCard;
	}
}





