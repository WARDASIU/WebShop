package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.service.invoice.InvoiceReceiver;
import com.wardasiu.project.wardasiu.service.invoice.InvoiceRow;

import java.io.File;
import java.util.List;

public interface InvoiceGenerator {
	File generateInvoice(InvoiceReceiver invoiceReceiver, List<InvoiceRow> invoiceRows, String path);
}
