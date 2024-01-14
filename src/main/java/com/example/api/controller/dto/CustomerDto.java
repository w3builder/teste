package com.example.api.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.api.domain.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
	
	private Long id;
	private String name;
	private String email;
	private String gender;
	private List<AddressDto> addresses;
	
	public CustomerDto(Customer entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.gender = entity.getGender();
		this.setAddresses(entity.getAddresses().stream().map(AddressDto::new).collect(Collectors.toList()));
	}
	
	public Customer toModel() {
		return new Customer(this.id, this.name, this.email, this.gender, 
				this.addresses.stream().map(address -> address.toModel())
				.collect(Collectors.toList()));
	}
}
