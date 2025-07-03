package com.realestate.main.excelOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.main.entity.Plots;
import com.realestate.main.entity.PropertyStatus;
import com.realestate.main.entity.Venture;


@Service
public class PlotExcelService {
	
	@Autowired
	private ExcelHeadersValidation excelHeadersValidation;

	public List<Plots> parseAndValidate(MultipartFile file, Venture venture) throws IOException {
		List<Plots> list = new ArrayList<>();
		List<String> headers = List.of("Plot Number", "Plot Size", "Price", "Status", "Location", "Facing",
				"Corner Plot");

		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheetAt = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheetAt.iterator();

			int rowNumber = 0;
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNumber == 0) {
					if (!excelHeadersValidation.validateHeaders(row, headers)) {
						throw new RuntimeException("Excel Headers are Invalid :"
								+ excelHeadersValidation.getExpectedHeadersAsString(headers));
					}
					rowNumber++;
					continue;
				}

				Plots plots = new Plots();
				plots.setPlotNumber((long) row.getCell(0).getNumericCellValue());
				plots.setPlotSize(row.getCell(1).getNumericCellValue());
				plots.setPrice(row.getCell(2).getNumericCellValue());
				String status = row.getCell(3).getStringCellValue().trim().toUpperCase();
				try {
					plots.setStatus(PropertyStatus.valueOf(status));
				} catch (IllegalArgumentException e) {
					throw new RuntimeException("Invalid status in Excel Row " + (row.getRowNum() + 1)
							+ ".Allowed  values: AVAILABLE, BOOKED, SOLD");
				}
				plots.setLocation(row.getCell(4).getStringCellValue());
				plots.setFacing(row.getCell(5).getStringCellValue());
				plots.setCornerPlot(row.getCell(6).getBooleanCellValue());
				plots.setVenture(venture);
				plots.setAssignStatus(PropertyStatus.NOTASSIGNED);
				
				list.add(plots);
			}
		}catch (Exception e) {
            throw new RuntimeException("Excel Parsing Error: " + e.getMessage());
        }
		return list; 
	}
}
