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

import com.hairtransplant.project.entities.MedicalHistory;
import com.hairtransplant.project.entities.PersonalInformation;


public class MedicalHistoryExcelService {
	public static byte[] exportMedicalHistory(List<MedicalHistory> medicalHistoryList)
			throws IOException {
		try (Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Medical History");
			
			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Allergies");
			headerRow.createCell(2).setCellValue("Current Medications");
			headerRow.createCell(3).setCellValue("PreExisting Conditions");
			headerRow.createCell(4).setCellValue("Previous Transplants");
			headerRow.createCell(5).setCellValue("Date Data Entry");
			headerRow.createCell(6).setCellValue("Personal Information ID");
			
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
			for (MedicalHistory medicalHistory : medicalHistoryList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(medicalHistory.getId());
				row.createCell(1).setCellValue(medicalHistory.getAllergies());
				row.createCell(2).setCellValue(medicalHistory.getCurrentMedications());
				row.createCell(3).setCellValue(medicalHistory.getPreExistingConditions());
				row.createCell(4).setCellValue(medicalHistory.getPreviousTransplants());
				row.createCell(5).setCellValue(medicalHistory.getDateDataEntry());
				row.createCell(6).setCellValue(medicalHistory.getStringParent());
			}

			// Auto size columns
			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}
	 public static List<MedicalHistory> importExcelFile(byte[] data) throws Exception {
	        List<MedicalHistory> result = new ArrayList<>();

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

	            // Create a new MedicalHistory object from the row data
	            MedicalHistory mh = new MedicalHistory();
	            String value = getStringValue(row.getCell(0));
	            Long id;
	            try {
	            	double doubleVal = Double.parseDouble(value);
	                id = (long) doubleVal;
	            } catch (NumberFormatException e) {
	                id = null; // set a default value or return null
	            }
	            
	            mh.setId(id);
	            mh.setAllergies(getStringValue(row.getCell(1)));
	            mh.setCurrentMedications(getStringValue(row.getCell(2)));
	            mh.setPreExistingConditions(getStringValue(row.getCell(3)));
	            mh.setPreviousTransplants(getStringValue(row.getCell(4)));
	            mh.setDateDataEntry(getStringValue(row.getCell(5)));
	            PersonalInformation pi = null;
	            if(getStringValue(row.getCell(6)) != null) {
	            	pi = new PersonalInformation();
	            	double doubleVal = Double.parseDouble(getStringValue(row.getCell(6)));
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

	    
}
