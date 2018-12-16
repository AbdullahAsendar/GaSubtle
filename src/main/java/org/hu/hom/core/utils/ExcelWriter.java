/**
 *  Copyright (C) 2018  Abdullah Al-Shishani
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * 
 */
package org.hu.hom.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * This class generates excel sheets
 * 
 * @author asendar
 *
 */
public class ExcelWriter {

	/**
	 * @param title of the sheet
	 * @param rows to be exported
	 * @param file to save the sheet to
	 * @throws IOException if file could not be created
	 */
	public static void export(String title, List<List<Object>> rows, File file) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(title);

		int rowCount = 0;

		for (List<Object> rowData : rows) {
			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;

			for (Object field : rowData) {
				Cell cell = row.createCell(columnCount++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				} else if (field instanceof Double) {
					cell.setCellValue((Double) field);
				}
			}
		}

		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			workbook.write(outputStream);
		}

		workbook.close();
	}
}
