package com.wardasiu.project.wardasiu.service.invoice;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InvoiceReceiver {
	private String name;
	private String address;
	private String phone;
	private String email;
}
