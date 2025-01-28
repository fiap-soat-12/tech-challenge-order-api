package br.com.fiap.techchallenge.order.application.usecase.order;


import br.com.fiap.techchallenge.order.application.usecase.order.dto.CreateOrderDTO;
import br.com.fiap.techchallenge.order.domain.models.Order;

public interface CreateOrderUseCase {

	Order create(CreateOrderDTO input);

}
