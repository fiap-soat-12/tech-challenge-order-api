package br.com.fiap.techchallenge.order.application.usecase.customer;

import br.com.fiap.techchallenge.order.domain.models.Customer;

public interface FindCustomerByDocumentUseCase {

	Customer findByDocument(String document);

}
