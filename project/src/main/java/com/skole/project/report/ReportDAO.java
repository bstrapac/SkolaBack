package com.skole.project.report;

import java.util.List;

public interface ReportDAO {
	
	List<OcjenaRaw> getRawOcjene();
	
	List<?> getRawOsobe();
	
	ReportCard getReportCard(Integer id);

}
