package com.skole.project.report;

import java.util.List;

public interface ReportService {
	
	List<OcjenaRaw> getRawOcjene();
	
	List<?> getRawOsobe();
	
	ReportCard getReportCard(Integer id);

}
