package com.wardasiu.project.wardasiu.service;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.wardasiu.project.wardasiu.service.invoice.InvoiceReceiver;
import com.wardasiu.project.wardasiu.service.invoice.InvoiceRow;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class InvoiceGeneratorImpl implements InvoiceGenerator {
	@Override
	public File generateInvoice(InvoiceReceiver invoiceReceiver, List<InvoiceRow> invoiceRows, String path) {
		File file = new File(path);
		PdfDocument pdfDoc = null;
		try {
			pdfDoc = new PdfDocument(new PdfWriter(file.getPath()));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		Document doc = new Document(pdfDoc);
		
		Paragraph title = new Paragraph("Faktura");
		title.setFontSize(24);
		title.setFontColor(new DeviceRgb(100, 200, 200));
		
		LineSeparator separator = new LineSeparator(new SolidLine());
		
		Paragraph name = new Paragraph("Nabywca: " + invoiceReceiver.getName());
		name.setFontSize(12);
		name.setPaddingTop(20f);
		Paragraph address = new Paragraph("Adres: " + invoiceReceiver.getAddress());
		address.setFontSize(12);
		Paragraph phone = new Paragraph("Telefon: " + invoiceReceiver.getPhone());
		phone.setFontSize(12);
		Paragraph email = new Paragraph("E-mail: " + invoiceReceiver.getEmail());
		email.setFontSize(12);
		email.setPaddingBottom(20f);
		
		doc.add(title);
		doc.add(separator);
		doc.add(name);
		doc.add(address);
		doc.add(phone);
		doc.add(email);
		
		Table table = new Table(4);
		table.setWidth(new UnitValue(UnitValue.PERCENT, 100));
		table.addHeaderCell("Name");
		table.addHeaderCell("Description");
		table.addHeaderCell("Quantity");
		table.addHeaderCell("Price");
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setGroupingUsed(false);
		
		for (InvoiceRow invoiceRow : invoiceRows) {
			table.addCell(new Cell().add(new Paragraph(invoiceRow.getName())));
			table.addCell(new Cell().add(new Paragraph(invoiceRow.getDescription())));
			table.addCell(new Cell().add(new Paragraph(String.valueOf(invoiceRow.getQuantity()))));
			table.addCell(new Cell().add(new Paragraph(df.format(invoiceRow.getPrice()))));
		}
		
		table.addCell(new Cell().add(new Paragraph("Total")));
		table.addCell(new Cell().add(new Paragraph("")));
		table.addCell(new Cell().add(new Paragraph(String.valueOf((
				invoiceRows.stream()
						.mapToInt(InvoiceRow::getQuantity)
						.sum())))));
		table.addCell(new Cell().add(new Paragraph(df.format((
				invoiceRows.stream().map(invoiceRow -> invoiceRow
								.getPrice()
								.multiply(new BigDecimal(invoiceRow.getQuantity())))
						.reduce(BigDecimal.ZERO, BigDecimal::add))
		))));
		
		table.setBackgroundColor(new DeviceRgb(0xA6, 0xCB, 0x0B));
		table.getHeader().setBackgroundColor(new DeviceRgb(0xCC, 0xCC, 0xCC));
		doc.add(table);
		doc.close();
		return file;
	}
}
