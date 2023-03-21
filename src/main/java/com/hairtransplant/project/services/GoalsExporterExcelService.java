package com.hairtransplant.project.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hairtransplant.project.entities.Goals;
import com.hairtransplant.project.entities.PersonalInformation;

public class GoalsExporterExcelService {
	public static byte[] exportGoals(List<Goals> GoalsList)
			throws IOException {
		try (Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Medical History");
			
			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Budget");
			headerRow.createCell(2).setCellValue("Expectations");
			headerRow.createCell(3).setCellValue("Outcome");
			headerRow.createCell(4).setCellValue("Date Data Entry");
			headerRow.createCell(5).setCellValue("Personal Information ID");
			
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = workbook.createFont();  
            font.setFontHeightInPoints((short)11);  
            font.setFontName("Courier New");  
            font.setItalic(true);  
            font.setBold(true);
			style.setFont(font);
			headerRow.setRowStyle(style);
			
			
			// Create data rows
			int rowNum = 1;
			for (Goals goals : GoalsList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(goals.getId());
				row.createCell(1).setCellValue(goals.getBudget());
				row.createCell(2).setCellValue(goals.getExpectations());
				row.createCell(3).setCellValue(goals.getOutcome());
				row.createCell(4).setCellValue(goals.getDateDataEntry());
				row.createCell(5).setCellValue(goals.getStringParent());
			}

			// Auto size columns
			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}
	 public static List<Goals> importExcelFile(byte[] data) throws Exception {
	        List<Goals> result = new ArrayList<>();

	        // Open the Excel byte array
	        ByteArrayInputStream bis = new ByteArrayInputStream(data);
	        Workbook workbook = WorkbookFactory.create(bis);

	        // Read the first sheet
	        Sheet sheet = workbook.getSheetAt(0);

	        // Iterate over the rows
	        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
	            Row row = sheet.getRow(i);
	            if (row == null) {
	                continue;
	            }

	            // Create a new Goals object from the row data
	            Goals mh = new Goals();
	            String value = getStringValue(row.getCell(0));
	            Long id;
	            try {
	            	double doubleVal = Double.parseDouble(value);
	                id = (long) doubleVal;
	            } catch (NumberFormatException e) {
	                id = null; // set a default value or return null
	            }
	            
	            mh.setId(id);
	            mh.setBudget(getIntegerValue(row.getCell(1)));
	            mh.setExpectations(getStringValue(row.getCell(2)));
	            mh.setOutcome(getStringValue(row.getCell(3)));
	            mh.setDateDataEntry(getStringValue(row.getCell(4)));
	            PersonalInformation pi = null;
	            if(getStringValue(row.getCell(5)) != null) {
	            	pi = new PersonalInformation();
	            	double doubleVal = Double.parseDouble(getStringValue(row.getCell(5)));
	            	pi.setId((long) doubleVal);
	            }
	            if(pi != null) {
	            	mh.setPersonalInformation(pi);	            	
	            }
	            
	            result.add(mh);
	        }

	        // Close the workbook and byte array input stream
	        workbook.close();
	        bis.close();

	        return result;
	    }

	 private static String getStringValue(Cell cell) {
		    if (cell == null) {
		        return null;
		    }

		    if (cell.getCellType() == CellType.NUMERIC) {
		        return String.valueOf(cell.getNumericCellValue());
		    } else {
		        return cell.getStringCellValue();
		    }
		}
	  private static Integer getIntegerValue(Cell cell) {
	        if (cell == null) {
	            return null;
	        }

	        return (int) cell.getNumericCellValue();
	    }

}
