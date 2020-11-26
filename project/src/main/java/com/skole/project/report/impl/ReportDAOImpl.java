package com.skole.project.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.skole.project.report.ReportDAO;
import com.skole.project.report.entity.OcjenaRaw;
import com.skole.project.report.entity.OsobaRaw;
import com.skole.project.report.entity.PredmetOsobaRaw;
import com.skole.project.report.entity.ReportCard;
import com.skole.project.report.mappers.OcjenaRawMapper;
import com.skole.project.report.mappers.OsobaRawMapper;
import com.skole.project.report.mappers.PredmetOsobaRawMapper;
@Repository
public class ReportDAOImpl implements ReportDAO {
	
	@Autowired
	@Qualifier(value="skolaJdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Override
	public List<OcjenaRaw> getRawOcjene() {
		
		List<OcjenaRaw> rawData = null;
		final String SQL_GET ="select * from ocjene;";
		rawData = jdbcTemplate.query(SQL_GET, new OcjenaRawMapper());
		return rawData;
	}

	@Override
	public List<OsobaRaw> getRawOsobe() {
		List<OsobaRaw> rawData = null;
		final String SQL_GET = "select * from osoba";
		rawData = jdbcTemplate.query(SQL_GET, new OsobaRawMapper());
		return rawData;
	}

	@Override
	public List<PredmetOsobaRaw> getPredmetOsobaRaw() {
		List<PredmetOsobaRaw> rawData = null;
		final String SQL_GET = "select * from predmet_osoba";
		rawData = jdbcTemplate.query(SQL_GET, new PredmetOsobaRawMapper());
		return rawData;
	}

	@Override
	public ReportCard getReportCard(Integer id) {
		ReportCard reportCard = new ReportCard();
		List<HashMap<String, Double>> avg = new ArrayList<>();
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
		
		jdbcTemplate.query(SQL_REPORT, new Object [] {id}, new RowMapper<HashMap<String, Double>>() {
			HashMap<String, Double> results = new HashMap<>();
			@Override
			public HashMap<String, Double> mapRow(ResultSet rs, int rowNum) throws SQLException {
				do{
					results = new HashMap<>();
					results.put(rs.getString("predmet"),rs.getDouble("avg"));
					avg.add(results);
						
				} while(rs.next());
				return null;
			}
			
		});
		
		finalGrade = jdbcTemplate.queryForObject(SQL_FINAL_GRADE, new Object[] {id},  Double.class);
		
		reportCard.setAvgOcjene(avg);
		reportCard.setFinalGrade(finalGrade);

		return reportCard;
	}
}
