package com.example.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.api.domain.model.Customer;

public interface CustomerService extends CrudService<Long, Customer> {
	
	List<Customer> findByNameLike(String name);
	Page<Customer> findAllPageable(Pageable pageable);
	List<Customer> findByNameLikeOrEmailOrGender(String name, String email, String gender);
	List<Customer> findByAddressesCityAndState(String city, String state);
}
