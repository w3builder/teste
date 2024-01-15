package com.example.api.controller.dto;

import com.example.api.domain.model.Address;
import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
	
	@JsonAlias("cep")
	private String postalcode;
	
	@JsonAlias("logradouro")
    private String street;
	
	@JsonAlias("complemento")
	private String complement;
	
	@JsonAlias("bairro")
	private String neighborhood;
	
	@JsonAlias("localidade")
	private String city;
	
	@JsonAlias("uf")
	private String state;
	
	public AddressDto(Address entity) {
		this.postalcode = entity.getPostalcode();
	    this.street = entity.getStreet();
		this.complement = entity.getComplement();
		this.neighborhood = entity.getNeighborhood();
		this.city = entity.getCity();
		this.state = entity.getState();
	}
	
	public Address toModel() {
		return Address.builder()
				.postalcode(this.postalcode)
				.state(this.street)
				.complement(this.complement)
				.neighborhood(this.neighborhood)
				.city(this.city)
				.state(this.state)
				.build();
	}
}
