package com.example.api.controller.dto;

import com.example.api.domain.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
	private String name;
	private String email;
	private String gender;
	
	public CustomerDto(Customer customer) {
		this.name = customer.getName();
		this.email = customer.getEmail();
		this.gender = customer.getGender();
	}
	
	public Customer toModel() {
		return new Customer(this.name, this.email, this.gender);
	}
}
