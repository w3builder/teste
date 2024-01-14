package com.example.api.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESS")
public class Address {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String postalcode;
    private String street;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Address(String postalcode, String street, String complement, 
    		String neighborhood, String city, String state) {
    	this.postalcode = postalcode;
    	this.street = street;
    	this.complement = complement;
    	this.neighborhood = neighborhood;
    	this.city = city;
    	this.state = state;
    }
}
