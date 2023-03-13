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

import com.hairtransplant.project.entities.LifeStyle;
import com.hairtransplant.project.entities.PersonalInformation;
import com.hairtransplant.project.enums.YesNoEnum;

public class LifeStyleExporterExcelService {
	public static byte[] exportLifeStyle(List<LifeStyle> LifeStyleList)
			throws IOException {
		try (Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Life Style");
			
			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Alcohol");
			headerRow.createCell(2).setCellValue("Diet");
			headerRow.createCell(3).setCellValue("Exercice");
			headerRow.createCell(4).setCellValue("Tobacco");
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
			for (LifeStyle lifeStyle : LifeStyleList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(lifeStyle.getId());
				if(lifeStyle.getAlcohol().equals(YesNoEnum.NO)) {
					row.createCell(1).setCellValue("NO");
				}else if(lifeStyle.getAlcohol().equals(YesNoEnum.YES)) {
					row.createCell(1).setCellValue("YES");
				}
				row.createCell(2).setCellValue(lifeStyle.getDiet());
				row.createCell(3).setCellValue(lifeStyle.getExercise());
				row.createCell(4).setCellValue(lifeStyle.getTobacco().toString());
				row.createCell(5).setCellValue(lifeStyle.getDateDataEntry());
				row.createCell(6).setCellValue(lifeStyle.getStringParent());
			}

			// Auto size columns
			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}
	 public static List<LifeStyle> importExcelFile(byte[] data) throws Exception {
	        List<LifeStyle> result = new ArrayList<>();

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

	            // Create a new LifeStyle object from the row data
	            LifeStyle mh = new LifeStyle();
	            String value = getStringValue(row.getCell(0));
	            Long id;
	            try {
	            	double doubleVal = Double.parseDouble(value);
	                id = (long) doubleVal;
	            } catch (NumberFormatException e) {
	                id = null; // set a default value or return null
	            }
	            
	            mh.setId(id);
	            if(getStringValue(row.getCell(1)).equals("NO")) {
	            	mh.setAlcohol(YesNoEnum.NO);
	            }else if(getStringValue(row.getCell(1)).equals("YES")){
	            	mh.setAlcohol(YesNoEnum.YES);
	            }
	            mh.setDiet(getStringValue(row.getCell(2)));
	            mh.setExercise(getStringValue(row.getCell(3)));
	            if(getStringValue(row.getCell(4)).equals("NO")) {
	            	mh.setTobacco(YesNoEnum.NO);
	            }else if(getStringValue(row.getCell(4)).equals("YES")){
	            	mh.setTobacco(YesNoEnum.YES);
	            }
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
