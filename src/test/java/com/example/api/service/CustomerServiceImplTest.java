package com.example.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.example.api.controller.dto.AddressDto;
import com.example.api.controller.dto.CustomerDto;
import com.example.api.domain.model.Customer;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testFindAllPageableSuccess() {
        Page<Customer> result = customerService.findAllPageable(PageRequest.of(0, 5));
        assertNotNull(result);
    }

    @Test
    public void testFindById() {
        Long customerId = 1L;
        Customer result = customerService.findById(customerId);
        assertNotNull(result);
    }

    @Test
    public void testFindAll() {
        List<Customer> result = customerService.findAll();
        assertNotNull(result);
    }

    @Test
    public void testFindByNameLike() {
        String name = "Ana"; 
        List<Customer> result = customerService.findByNameLike(name);
        assertNotNull(result);
    }

    @Test
    public void testFindByNameLikeOrEmailOrGender() {
        
    	String name = "Ana";
        String email = "Ana@email.com";
        String gender = "Female";

        List<Customer> result = customerService.findByNameLikeOrEmailOrGender(name, email, gender);
        assertNotNull(result);
    }

    @Test
    public void testFindByAddressesCityAndState() {
        
    	String city = "Fortaleza";
        String state = "CE";
        
        List<Customer> result = customerService.findByAddressesCityAndState(city, state);
        assertNotNull(result);
    }

    @Test
    public void testCreate() {
    	
    	List<AddressDto> addresses = new ArrayList<>();
    	
    	AddressDto addressDto = AddressDto.builder()
    			.postalcode("60000000")
    			.street("5 avenue")
    			.complement("111")
    			.neighborhood("Broklin")
    			.city("New York")
    			.state("NY").build();
    	
    	addresses.add(addressDto);

    	CustomerDto customerDto = CustomerDto.builder()
    			.name("Tommy")
    			.email("tommy@gmail.com")
    			.gender("Male")
    			.addresses(addresses)
    			.build();
    	
    	Customer result = customerService.create(customerDto.toModel());
    	assertNotNull(result);				
    }

    @Test
    public void testUpdate() {
    	
    	List<AddressDto> addresses = new ArrayList<>();
    	
    	AddressDto addressDto = AddressDto.builder()
    			.postalcode("60000000")
    			.street("5 avenue")
    			.complement("111")
    			.neighborhood("Broklin")
    			.city("New York")
    			.state("NY").build();
    	
    	addresses.add(addressDto);

    	CustomerDto customerDto = CustomerDto.builder()
    			.name("Tommy")
    			.email("tommy2@gmail.com")
    			.gender("Male")
    			.addresses(addresses)
    			.build();
    	
    	Customer result = customerService.update(2L, customerDto.toModel());
    	
    	assertNotNull(result);	
    }

    @Test
    public void testDelete() {
    	customerService.delete(3L);
    }
}
