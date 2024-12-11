package com.zodiac.homehealthdevicedatalogger.Util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.zodiac.homehealthdevicedatalogger.Data.PatientHealthData;
import com.zodiac.homehealthdevicedatalogger.Models.User;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReportGenerationService {

	// Method to generate Excel report
	public void generateExcelReport(User personalInfo, List<PatientHealthData> reportData, Stage stage) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Report");

		// Add personal info headers
		String[] personalHeaders = {
				"First Name", "Last Name", "Age", "Blood Group", "Phone Number", "Gender", "Email", "Role Name"
		};
		Row personalHeaderRow = sheet.createRow(0); // Row 0 for personal headers
		for (int i = 0; i < personalHeaders.length; i++) {
			Cell cell = personalHeaderRow.createCell(i);
			cell.setCellValue(personalHeaders[i]);
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);
			cell.setCellStyle(style);
		}

		// Add personal info data
		Row personalInfoRow = sheet.createRow(1); // Row 1 for personal data
		personalInfoRow.createCell(0).setCellValue(personalInfo.getFirstName());
		personalInfoRow.createCell(1).setCellValue(personalInfo.getLastName());
		personalInfoRow.createCell(2).setCellValue(personalInfo.getAge());
		personalInfoRow.createCell(3).setCellValue(personalInfo.getBloodGroup());
		personalInfoRow.createCell(4).setCellValue(personalInfo.getPhone());
		personalInfoRow.createCell(5).setCellValue(personalInfo.getGender());
		personalInfoRow.createCell(6).setCellValue(personalInfo.getEmail());
		personalInfoRow.createCell(8).setCellValue(personalInfo.getRole());

		// Add health data headers
		int headerRowIdx = 3; // Start after personal info
		Row headerRow = sheet.createRow(headerRowIdx);
		String[] healthHeaders = { "Date", "Blood Pressure", "Sugar Level", "Heart Rate", "Oxygen Level" };
		for (int i = 0; i < healthHeaders.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(healthHeaders[i]);
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);
			cell.setCellStyle(style);
		}

		// Add health data
		int rowNum = headerRowIdx + 1;
		for (PatientHealthData data : reportData) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(data.getDate().toString());
			row.createCell(1).setCellValue(data.getBloodPressure());
			row.createCell(2).setCellValue(data.getSugarLevel());
			row.createCell(3).setCellValue(data.getHeartRate());
			row.createCell(4).setCellValue(data.getOxygenLevel());
		}

		for (int i = 0; i < personalHeaders.length; i++) {
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < healthHeaders.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Save file using FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
			try (FileOutputStream fileOut = new FileOutputStream(file)) {
				workbook.write(fileOut);
				showAlert(Alert.AlertType.INFORMATION, "Success", "Excel report saved successfully at: " + file.getAbsolutePath());
			} catch (IOException e) {
				showAlert(Alert.AlertType.ERROR, "Error", "Failed to save Excel report: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// Method to generate PDF report (Stub for now)
	public void generatePDFReport(User personalInfo, List<PatientHealthData> reportData, Stage stage) {
		// FileChooser for saving PDF file
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
			try {
				// Create a new document
				Document document = new Document();
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();

				// Add title to the document
				com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
				Paragraph title = new Paragraph("Health Data Report", (com.itextpdf.text.Font) titleFont);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);

				// Add personal information
				document.add(new Paragraph("\nPersonal Information"));
				PdfPTable personalInfoTable = new PdfPTable(2);
				personalInfoTable.setWidthPercentage(100);
				personalInfoTable.setSpacingBefore(10f);

				addTableCell(personalInfoTable, "First Name:");
				addTableCell(personalInfoTable, personalInfo.getFirstName() != null ? personalInfo.getFirstName() : "N/A");

				addTableCell(personalInfoTable, "Last Name:");
				addTableCell(personalInfoTable, personalInfo.getLastName() != null ? personalInfo.getLastName() : "N/A");

				addTableCell(personalInfoTable, "Age:");
				addTableCell(personalInfoTable, String.valueOf(personalInfo.getAge()));

				addTableCell(personalInfoTable, "Blood Group:");
				addTableCell(personalInfoTable, personalInfo.getBloodGroup() != null ? personalInfo.getBloodGroup() : "N/A");

				addTableCell(personalInfoTable, "Phone Number:");
				addTableCell(personalInfoTable, personalInfo.getPhone() != null ? personalInfo.getPhone() : "N/A");

				addTableCell(personalInfoTable, "Gender:");
				addTableCell(personalInfoTable, personalInfo.getGender() != null ? personalInfo.getGender() : "N/A");

				addTableCell(personalInfoTable, "Email:");
				addTableCell(personalInfoTable, personalInfo.getEmail() != null ? personalInfo.getEmail() : "N/A");

				addTableCell(personalInfoTable, "Role ID:");
				addTableCell(personalInfoTable, personalInfo.getRoleID() != null ? String.valueOf(personalInfo.getRoleID()) : "N/A");

				addTableCell(personalInfoTable, "Role Name:");
				addTableCell(personalInfoTable, personalInfo.getRole() != null ? personalInfo.getRole() : "N/A");

				document.add(personalInfoTable);

				// Add health data
				document.add(new Paragraph("\nHealth Data"));
				PdfPTable healthDataTable = new PdfPTable(5);
				healthDataTable.setWidthPercentage(100);
				healthDataTable.setSpacingBefore(10f);

				addTableCell(healthDataTable, "Date", true);
				addTableCell(healthDataTable, "Blood Pressure", true);
				addTableCell(healthDataTable, "Sugar Level", true);
				addTableCell(healthDataTable, "Heart Rate", true);
				addTableCell(healthDataTable, "Oxygen Level", true);

				for (PatientHealthData data : reportData) {
					addTableCell(healthDataTable, data.getDate() != null ? String.valueOf(data.getDate()) : "N/A");
					addTableCell(healthDataTable, data.getBloodPressure() != null ? data.getBloodPressure() : "N/A");
					addTableCell(healthDataTable, data.getSugarLevel() != null ? data.getSugarLevel() : "N/A");
					addTableCell(healthDataTable, data.getHeartRate() != 0 ? String.valueOf(data.getHeartRate()) : "N/A");
					addTableCell(healthDataTable, data.getOxygenLevel() != 0 ? String.valueOf(data.getOxygenLevel()) : "N/A");
				}

				document.add(healthDataTable);

				// Close the document
				document.close();

				showAlert(Alert.AlertType.INFORMATION, "PDF Report Saved", "PDF report saved successfully at: " + file.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
				showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate the PDF file. Error: " + e.getMessage());
			}
		}
	}

	private void addTableCell(PdfPTable table, String text) {
		addTableCell(table, text, false);
	}

	private void addTableCell(PdfPTable table, String text, boolean isHeader) {
		com.itextpdf.text.Font font = isHeader ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12) : FontFactory.getFont(FontFactory.HELVETICA, 12);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPadding(5);
		if (isHeader) {
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		}
		table.addCell(cell);
	}

	private void showAlert(Alert.AlertType type, String title, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
