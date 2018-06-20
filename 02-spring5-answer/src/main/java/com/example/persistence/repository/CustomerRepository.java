package com.example.persistence.repository;

import com.example.persistence.entity.Customer;
import org.springframework.data.repository.CrudRepository;

// TODO 1-07 Spring DataのCrudRepository<Customer, Integer>を継承する
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
