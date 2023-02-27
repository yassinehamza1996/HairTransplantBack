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

import com.hairtransplant.project.entities.PersonalInformation;

public class PersonalInformationExcelService {

	public static byte[] exportPersonalInformationToExcel(List<PersonalInformation> personalInformationList)
			throws IOException {
		try (Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Personal Information");
			
			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("First Name");
			headerRow.createCell(2).setCellValue("Last Name");
			headerRow.createCell(3).setCellValue("Address");
			headerRow.createCell(4).setCellValue("Email");
			headerRow.createCell(5).setCellValue("Phone Number");
			headerRow.createCell(6).setCellValue("Age");

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
			for (PersonalInformation personalInformation : personalInformationList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(personalInformation.getId());
				row.createCell(1).setCellValue(personalInformation.getFirstname());
				row.createCell(2).setCellValue(personalInformation.getLastname());
				row.createCell(3).setCellValue(personalInformation.getAddress());
				row.createCell(4).setCellValue(personalInformation.getEmail());
				row.createCell(5).setCellValue(personalInformation.getPhoneNumber());
				row.createCell(6).setCellValue(personalInformation.getAge());
			}

			// Auto size columns
			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}
	 public static List<PersonalInformation> importExcelFile(byte[] data) throws Exception {
	        List<PersonalInformation> result = new ArrayList<>();

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

	            // Create a new PersonalInformation object from the row data
	            PersonalInformation pi = new PersonalInformation();
	            String value = getStringValue(row.getCell(0));
	            Long id;
	            try {
	            	double doubleVal = Double.parseDouble(value);
	                id = (long) doubleVal;
	            } catch (NumberFormatException e) {
	                id = null; // set a default value or return null
	            }
	            pi.setId(id);
	            pi.setFirstname(getStringValue(row.getCell(1)));
	            pi.setLastname(getStringValue(row.getCell(2)));
	            pi.setAddress(getStringValue(row.getCell(3)));
	            pi.setEmail(getStringValue(row.getCell(4)));
	            pi.setPhoneNumber(getStringValue(row.getCell(5)));
	            pi.setAge(getIntegerValue(row.getCell(6)));

	            result.add(pi);
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
