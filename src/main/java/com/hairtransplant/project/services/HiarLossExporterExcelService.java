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

import com.hairtransplant.project.entities.HairLoss;
import com.hairtransplant.project.entities.HairLoss;
import com.hairtransplant.project.entities.PersonalInformation;
import com.hairtransplant.project.enums.YesNoEnum;

public class HiarLossExporterExcelService {
	public static byte[] exportHairLoss(List<HairLoss> hairLossList) throws IOException {
		try (Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Life Style");

			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Cause");
			headerRow.createCell(2).setCellValue("Extent");
			headerRow.createCell(3).setCellValue("Pattern");
			headerRow.createCell(4).setCellValue("Date Data Entry");
			headerRow.createCell(5).setCellValue("Personal Information ID");

			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Courier New");
			font.setItalic(true);
			font.setBold(true);
			style.setFont(font);
			headerRow.setRowStyle(style);

			// Create data rows
			int rowNum = 1;
			for (HairLoss HairLoss : hairLossList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(HairLoss.getId());
				row.createCell(1).setCellValue(HairLoss.getCause());
				row.createCell(2).setCellValue(HairLoss.getExtent());
				row.createCell(3).setCellValue(HairLoss.getPattern());
				row.createCell(4).setCellValue(HairLoss.getDateDataEntry());
				row.createCell(5).setCellValue(HairLoss.getStringParent());
			}

			// Auto size columns
			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}

	public static List<HairLoss> importExcelFile(byte[] data) throws Exception {
		List<HairLoss> result = new ArrayList<>();

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

			// Create a new HairLoss object from the row data
			HairLoss mh = new HairLoss();
			String value = getStringValue(row.getCell(0));
			Long id;
			try {
				double doubleVal = Double.parseDouble(value);
				id = (long) doubleVal;
			} catch (NumberFormatException e) {
				id = null; // set a default value or return null
			}

			mh.setId(id);
			mh.setCause(getStringValue(row.getCell(1)));
			mh.setExtent(getStringValue(row.getCell(2)));
			mh.setPattern(getStringValue(row.getCell(3)));
			mh.setDateDataEntry(getStringValue(row.getCell(5)));
			PersonalInformation pi = null;
			if (getStringValue(row.getCell(6)) != null) {
				pi = new PersonalInformation();
				double doubleVal = Double.parseDouble(getStringValue(row.getCell(6)));
				pi.setId((long) doubleVal);
			}
			if (pi != null) {
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
