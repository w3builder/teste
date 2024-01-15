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
	
	private String name;
	private String email;
	private String gender;
	private List<AddressDto> addresses;
	
	public CustomerDto(Customer entity) {
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.gender = entity.getGender();
		this.setAddresses(entity.getAddresses().stream().map(AddressDto::new).collect(Collectors.toList()));
	}
	
	public Customer toModel() {
		return Customer.builder()
				.name(this.name)
				.email(this.email)
				.gender(this.gender)
				.addresses(this.addresses.stream().map(address -> address.toModel())
				.collect(Collectors.toList())).build();
	}
}
