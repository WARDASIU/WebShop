package com.wardasiu.project.wardasiu.service;

import com.google.common.io.Resources;
import com.wardasiu.project.wardasiu.service.invoice.InvoiceReceiver;
import com.wardasiu.project.wardasiu.service.invoice.InvoiceRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class InvoiceGeneratorTest {
	@Autowired
	InvoiceGenerator invoiceGenerator;
	
	@Autowired
	EmailService emailService;
	
	@Test
	void generateInvoiceTest() {
		InvoiceReceiver invoiceReceiver = InvoiceReceiver.builder()
				.name("Jan Kowalski")
				.address("00-000 Warszawa ul. Wiejska 12")
				.phone("+48 123 456 789")
				.email("test@gmail.com")
				.build();
		List<InvoiceRow> invoiceRows = List.of(
				InvoiceRow.builder()
						.name("Item 1")
						.description("Description 1")
						.quantity(1)
						.price(BigDecimal.valueOf(100))
						.build(),
				InvoiceRow.builder()
						.name("Item 2")
						.description("Description 2")
						.quantity(2)
						.price(BigDecimal.valueOf(250))
						.build(),
				InvoiceRow.builder()
						.name("Item 3")
						.description("Description 3")
						.quantity(30)
						.price(BigDecimal.valueOf(50.99))
						.build()
		);
		ClassLoader classLoader = getClass().getClassLoader();
		String file = Objects.requireNonNull(classLoader.getResource("invoice.pdf")).getPath();
		file = file.substring(1);

		invoiceGenerator.generateInvoice(invoiceReceiver, invoiceRows, file);
	}
	
	@Test
	void generateInvoiceAndSendEmailTest() {
		InvoiceReceiver invoiceReceiver = InvoiceReceiver.builder()
				.name("Jan Kowalski")
				.address("00-000 Warszawa ul. Wiejska 12")
				.phone("+48 123 456 789")
				.email("test@gmail.com")
				.build();
		List<InvoiceRow> invoiceRows = List.of(
				InvoiceRow.builder()
						.name("Item 1")
						.description("Description 1")
						.quantity(1)
						.price(BigDecimal.valueOf(100))
						.build(),
				InvoiceRow.builder()
						.name("Item 2")
						.description("Description 2")
						.quantity(2)
						.price(BigDecimal.valueOf(250))
						.build(),
				InvoiceRow.builder()
						.name("Item 3")
						.description("Description 3")
						.quantity(30)
						.price(BigDecimal.valueOf(50.99))
						.build()
		);
		ClassLoader classLoader = getClass().getClassLoader();
		String filePath = Objects.requireNonNull(classLoader.getResource("invoice.pdf")).getPath();
		filePath = filePath.substring(1);

		File file = invoiceGenerator.generateInvoice(invoiceReceiver, invoiceRows, filePath);
		emailService.sendSimpleEmailWithAttachments("easystepapi@gmail.com", "Test", "Test body", file);
	}
}