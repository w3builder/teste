package com.example.api.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByNameLike(String name);
	
	List<Customer> findByNameLikeOrEmailOrGender(String name, String email, String gender);
	
	Customer findByEmail(String email);
	
	List<Customer> findByGender(String gender);
	
	boolean existsByEmail(String number);
	
	Page<Customer> findAll(Pageable pageable);

}
