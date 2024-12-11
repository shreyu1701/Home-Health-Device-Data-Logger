package com.zodiac.homehealthdevicedatalogger.Util;

import com.itextpdf.text.pdf.PdfPTable;
import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Models.User;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ReportGeneratorTechnician {

	public void generateExcelReport(User user, List<Patient> healthDataList, Stage stage) throws IOException {
		// Create a FileChooser to allow the user to select the location and filename for saving the Excel file
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
		fileChooser.setInitialFileName("HealthReport_" + user.getId() + ".xlsx");

		// Show the Save As dialog and get the selected file
		File selectedFile = fileChooser.showSaveDialog(stage);

		// If the user selects a file, generate the report
		if (selectedFile != null) {
			// Create a workbook and a sheet
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Health Report");

			// Create the header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Personal Info");
			headerRow.createCell(1).setCellValue("Health Metrics");

			// Add personal information (name, age, gender, etc.)
			Row personalInfoRow = sheet.createRow(1);
			personalInfoRow.createCell(0).setCellValue("Name: " + user.getFirstName() + " " + user.getLastName());
			personalInfoRow.createCell(1).setCellValue("Age: " + user.getAge());
			personalInfoRow.createCell(2).setCellValue("Gender: " + user.getGender());
			personalInfoRow.createCell(3).setCellValue("Blood Group: " + user.getBloodGroup());
			personalInfoRow.createCell(4).setCellValue("Email: " + user.getEmail());

			// Add health data and calculate averages
			int rowNum = 2;
			double totalBloodPressure = 0;
			double totalSugarLevel = 0;
			double totalHeartRate = 0;
			double totalOxygenLevel = 0;
			int count = healthDataList.size();

			for (Patient data : healthDataList) {
				Row healthDataRow = sheet.createRow(rowNum++);
				healthDataRow.createCell(0).setCellValue(data.getDate().toString());
				healthDataRow.createCell(1).setCellValue(data.getBloodPressure() != null ? data.getBloodPressure() : "N/A");
				healthDataRow.createCell(2).setCellValue(data.getSugarLevel() != null ? data.getSugarLevel() : "N/A");
				healthDataRow.createCell(3).setCellValue(data.getHeartRate());
				healthDataRow.createCell(4).setCellValue(data.getOxygenLevel());

				// Add to totals for averaging
				if (data.getBloodPressure() != null) {
					totalBloodPressure += Integer.parseInt(data.getBloodPressure().split("/")[0]); // Systolic
				}
				if (data.getSugarLevel() != null) {
					totalSugarLevel += Integer.parseInt(data.getSugarLevel());
				}
				totalHeartRate += data.getHeartRate();
				totalOxygenLevel += data.getOxygenLevel();
			}

			// Calculate averages
			Row averageRow = sheet.createRow(rowNum);
			averageRow.createCell(0).setCellValue("Averages:");
			averageRow.createCell(1).setCellValue("Blood Pressure: " + (totalBloodPressure / count));
			averageRow.createCell(2).setCellValue("Sugar Level: " + (totalSugarLevel / count));
			averageRow.createCell(3).setCellValue("Heart Rate: " + (totalHeartRate / count));
			averageRow.createCell(4).setCellValue("Oxygen Level: " + (totalOxygenLevel / count));

			// Write the output to the selected file
			try (FileOutputStream fileOut = new FileOutputStream(selectedFile)) {
				workbook.write(fileOut);
			}
			workbook.close();
		} else {
			// Handle the case where the user cancels the file selection
			System.out.println("File selection was canceled.");
		}
	}

	public void generatePDFReport(User user, List<Patient> healthDataList, Stage stage) throws DocumentException, IOException {
		// Create FileChooser to allow user to select the location and filename for saving the PDF
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
		fileChooser.setInitialFileName("HealthReport_" + user.getId() + ".pdf");

		// Show the Save As dialog and get the selected file
		File selectedFile = fileChooser.showSaveDialog(stage);

		// If the user selects a file, generate the report
		if (selectedFile != null) {
			// Create a document and set up the file at the selected location
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
			document.open();

			// Add title
			document.add(new Paragraph("Health Report for User: " + user.getFirstName() + " " + user.getLastName()));
			document.add(Chunk.NEWLINE);

			// Add personal information
			document.add(new Paragraph("Name: " + user.getFirstName() + " " + user.getLastName()));
			document.add(new Paragraph("Age: " + user.getAge()));
			document.add(new Paragraph("Gender: " + user.getGender()));
			document.add(new Paragraph("Blood Group: " + user.getBloodGroup()));
			document.add(new Paragraph("Email: " + user.getEmail()));
			document.add(Chunk.NEWLINE);

			// Add health data table
			PdfPTable table = new PdfPTable(5);
			table.addCell("Date");
			table.addCell("Blood Pressure");
			table.addCell("Sugar Level");
			table.addCell("Heart Rate");
			table.addCell("Oxygen Level");

			double totalBloodPressure = 0;
			double totalSugarLevel = 0;
			double totalHeartRate = 0;
			double totalOxygenLevel = 0;

			int count = healthDataList.size();
			for (Patient data : healthDataList) {
				table.addCell(data.getDate().toString());
				table.addCell(data.getBloodPressure() != null ? data.getBloodPressure() : "N/A");
				table.addCell(data.getSugarLevel() != null ? data.getSugarLevel() : "N/A");
				table.addCell(String.valueOf(data.getHeartRate()));
				table.addCell(String.valueOf(data.getOxygenLevel()));

				// Add to totals for averaging
				if (data.getBloodPressure() != null) {
					totalBloodPressure += Integer.parseInt(data.getBloodPressure().split("/")[0]); // Systolic
				}
				if (data.getSugarLevel() != null) {
					totalSugarLevel += Integer.parseInt(data.getSugarLevel());
				}
				totalHeartRate += data.getHeartRate();
				totalOxygenLevel += data.getOxygenLevel();
			}

			// Add the table to the document
			document.add(table);

			// Add averages
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Averages:"));
			document.add(new Paragraph("Blood Pressure: " + (totalBloodPressure / count)));
			document.add(new Paragraph("Sugar Level: " + (totalSugarLevel / count)));
			document.add(new Paragraph("Heart Rate: " + (totalHeartRate / count)));
			document.add(new Paragraph("Oxygen Level: " + (totalOxygenLevel / count)));

			// Close the document
			document.close();
		} else {
			// Handle the case where the user cancels the file selection
			System.out.println("File selection was canceled.");
		}
	}
}