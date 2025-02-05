package br.com.fiap.techchallenge.order.application.usecase.customer.impl;

import br.com.fiap.techchallenge.order.application.exceptions.AlreadyExistsException;
import br.com.fiap.techchallenge.order.application.persistence.CustomerPersistence;
import br.com.fiap.techchallenge.order.application.usecase.customer.CreateCustomerUseCase;
import br.com.fiap.techchallenge.order.domain.models.Customer;

public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

	private final CustomerPersistence persistence;

	public CreateCustomerUseCaseImpl(CustomerPersistence persistence) {
		this.persistence = persistence;
	}

	@Override
	public Customer create(Customer customer) {
		var customerFound = persistence.findByDocument(customer.getDocument());

		if (customerFound.isPresent()) {
			throw new AlreadyExistsException("Customer Already Exists");
		}
		return persistence.create(customer);
	}
}
