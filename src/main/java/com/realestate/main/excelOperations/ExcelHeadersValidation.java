package com.realestate.main.excelOperations;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class ExcelHeadersValidation {
	
	public boolean validateHeaders(Row row, List<String> expectedHeaders) {
		if(row==null || expectedHeaders==null) return false;
		
		for(int i=0;i<expectedHeaders.size();i++) {
			Cell cell = row.getCell(i);
			if(cell==null || !cell.getStringCellValue().trim().equalsIgnoreCase(expectedHeaders.get(i))) return false;
		}
		return true;
	}
	
	public String getExpectedHeadersAsString(List<String> headers) {
		return String.join(", ", headers);
	}
}
