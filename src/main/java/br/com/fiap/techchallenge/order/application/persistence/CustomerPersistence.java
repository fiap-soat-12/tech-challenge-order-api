package br.com.fiap.techchallenge.order.application.persistence;


import br.com.fiap.techchallenge.order.domain.models.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerPersistence {

	Customer create(Customer customer);

	Optional<Customer> findByDocument(String document);

	Optional<Customer> findById(UUID id);

}
