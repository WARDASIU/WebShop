package com.wardasiu.project.wardasiu.service.invoice;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class InvoiceRow {
	private String name;
	private String description;
	private int quantity;
	private BigDecimal price;
}
