package com.example.persistence.repository;

import com.example.persistence.entity.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("SELECT id, first_name, last_name, email, birthday FROM customer" +
            " WHERE first_name LIKE :keyword OR last_name LIKE :keyword ORDER BY id")
    List<Customer> findByFirstNameOrLastName(String keyword);
}
