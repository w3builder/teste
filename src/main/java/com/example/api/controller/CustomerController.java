package com.example.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.api.controller.dto.AddressDto;
import com.example.api.controller.dto.CustomerDto;
import com.example.api.service.ViaCepService;
import com.example.api.service.impl.CustomerServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customers")
@Api(tags = "CustomerController")
public class CustomerController {

	private final CustomerServiceImpl service;
	private final ViaCepService viaCepService;

	public CustomerController(CustomerServiceImpl service, ViaCepService viaCepService) {
		this.service = service;
		this.viaCepService = viaCepService;
	}

	@GetMapping
	@ApiOperation(value = "Get all customers", notes = "Retrieve a list of all registered customers")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved")
    })
	public ResponseEntity<List<CustomerDto>> findAll() {
		var customers = service.findAll();
		var customersDto = customers.stream().map(CustomerDto::new).collect(Collectors.toList());
		return ResponseEntity.ok(customersDto);
	}
	
	@ApiOperation(value = "Get all customers with pageable", notes = "Retrieve a list of all registered customers with pageable")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved")
    })
	@GetMapping("/{page}/{size}")
	public ResponseEntity<List<CustomerDto>> getCustomers(
			@PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {

		Pageable pageable = PageRequest.of(page, size == 0 ? 10 : size);
		var customers = service.findAllPageable(pageable);
		var customersDto = customers.stream().map(CustomerDto::new).collect(Collectors.toList());
		return ResponseEntity.ok(customersDto);
	}

	@GetMapping("/{id}")
    @ApiOperation(value = "Get Customer by ID", notes = "Provide an ID to get the Customer details")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "Customer not found")
    })
	public ResponseEntity<CustomerDto> findById(@PathVariable Long id) {
		CustomerDto customerDto = new CustomerDto(service.findById(id));
		return ResponseEntity.ok(customerDto);
	}
	
	@GetMapping("/{name}")
	@ApiOperation(value = "Get curtomers find by name", notes = "Retrieve a list of customers find by name filter")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Name not found")
    })
	public ResponseEntity<List<CustomerDto>> fillAddresByServiceViaCep(@PathParam("name") String name) {
		var customers = service.findByNameLike("%"+name+"%");
		var cursomerDto = customers.stream().map(CustomerDto::new).collect(Collectors.toList());
		return ResponseEntity.ok(cursomerDto);
	}
	
	@GetMapping("/filter/{name}/{email}/{gender}")
	@ApiOperation(value = "Get curtomers find by filters", notes = "Retrieve a list of customers find by filters")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Name not found")
    })
	public ResponseEntity<List<CustomerDto>> findByCustomerByFilters(
				@PathParam("name") String name,
				@PathParam("email") String email,
				@PathParam("gender") String gender
			) {
		var customers = service.findByNameLikeOrEmailOrGender(
				"%"+name+"%", 
				email == null ? "" : email, 
				gender == null ? "" : gender
			);
		var cursomerDto = customers.stream().map(CustomerDto::new).collect(Collectors.toList());
		return ResponseEntity.ok(cursomerDto);
	}
	
	@GetMapping("/address/filter/{city}/{state}")
	@ApiOperation(value = "Get curtomers find by address filters", notes = "Retrieve a list of customers find by address filters")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Name not found")
    })
	public ResponseEntity<List<CustomerDto>> findByCustomerByAddressFilters(
				@PathParam("city") String city,
				@PathParam("state") String state
			) {
		var customers = service.findByAddressesCityAndState(city, state);
		var cursomerDto = customers.stream().map(CustomerDto::new).collect(Collectors.toList());
		return ResponseEntity.ok(cursomerDto);
	}
	
	@GetMapping("/address/insert/viacep/{cep}")
	@ApiOperation(value = "Fill customer address fields by ViaCepSerivce")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Name not found")
    })
	public ResponseEntity<AddressDto> fillAddresByViaCepService(@PathParam("cep") String cep) {
		var address = viaCepService.getAddressByCep(cep);
		return ResponseEntity.ok(address);
	}
	
	@ApiOperation(value = "Create new customer", notes = "Create a new customer and return the created customer's data")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer created successfully"),
            @ApiResponse(code = 422, message = "Invalid customer data provided")
    })
    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto dto) {
    	var customer = service.create(dto.toModel());
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    			.path("/{id}")
    			.buildAndExpand(customer.getId())
    			.toUri();
    	return ResponseEntity.created(location).body(new CustomerDto(customer));
    }
    
	@ApiOperation(value = "Update customer")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer updated successfully"),
            @ApiResponse(code = 404, message = "Customer not found"),
            @ApiResponse(code = 422, message = "Invalid customer data provided")
    })
    @PutMapping
    public ResponseEntity<CustomerDto> update(@RequestBody CustomerDto dto) {
    	var customer = service.update(dto.toModel());
    	return ResponseEntity.ok(new CustomerDto(customer));
    }

	@ApiOperation(value = "Delete a customer", notes = "Delete an existing customer based on its ID")
	@ApiResponses(value = {
            @ApiResponse(code = 204, message = "Customer deleted successfully"),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	service.delete(id);
    	return ResponseEntity.noContent().build();
    }

}
