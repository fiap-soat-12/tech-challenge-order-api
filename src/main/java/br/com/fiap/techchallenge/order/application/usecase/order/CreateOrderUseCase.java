package br.com.fiap.techchallenge.order.application.usecase.order;


import br.com.fiap.techchallenge.order.application.usecase.order.dto.CreateOrderDTO;
import br.com.fiap.techchallenge.order.domain.models.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CreateOrderUseCase {

	Order create(CreateOrderDTO input) throws JsonProcessingException;

}
