package com.example.api.service.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.domain.model.Customer;
import com.example.api.domain.repository.CustomerRepository;
import com.example.api.service.CustomerService;
import com.example.api.service.exception.BusinessException;
import com.example.api.service.exception.NotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService{

	private static final Long UNCHANGEABLE_CUSTOMER_ID = 1L;
	
	private final CustomerRepository repository;

	public CustomerServiceImpl(CustomerRepository repository) {
		this.repository = repository;
	}
	

	@Transactional(readOnly = true)
	public Page<Customer> findAllPageable(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Customer findById(Long id) {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		return (List<Customer>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Customer> findByNameLike(String name) {
		return repository.findByNameLike(name);
	}
	
	@Transactional(readOnly = true)
	public List<Customer> findByNameLikeOrEmailOrGender(String name, String email, String gender) {
		return repository.findByNameLikeOrEmailOrGender("%"+name+"%", email, gender);
	}

	@Transactional(readOnly = true)
	public List<Customer> findByAddressesCityAndState(String city, String state) {
		return repository.findByAddressesCityOrddressesState(city, state);
	}

	@Transactional
	public Customer create(Customer customerToCreate) {
		
		ofNullable(customerToCreate).orElseThrow(() -> new BusinessException("Customer to create must not be null."));

        this.validateChangeableId(customerToCreate.getId(), "created");
        
        if (repository.existsByEmail(customerToCreate.getEmail())) {
            throw new BusinessException("This email already exists.");
        }
        
        customerToCreate.getAddresses().forEach(a -> a.setCustomer(customerToCreate));
        return repository.save(customerToCreate);
	}

	@Transactional
	public Customer update(Customer customerToUpdate) {
		
		this.validateChangeableId(customerToUpdate.getId(), "updated");
		
		Customer dbCustomer = this.findById(customerToUpdate.getId());
		
        if (!dbCustomer.getId().equals(customerToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }
        
        if (repository.existsByEmail(customerToUpdate.getEmail())) {
            throw new BusinessException("This email already exists.");
        }

        customerToUpdate.getAddresses().forEach(a -> a.setCustomer(customerToUpdate));
        
        return this.repository.save(customerToUpdate);
	}

	@Transactional
	public void delete(Long id) {
		this.validateChangeableId(id, "deleted");
        Customer dbCustomer = this.findById(id);
        this.repository.delete(dbCustomer);
	}

	private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_CUSTOMER_ID.equals(id)) {
            throw new BusinessException(String.format("Customer with ID %d can not be %s.", UNCHANGEABLE_CUSTOMER_ID, operation));
        }
    }

}
