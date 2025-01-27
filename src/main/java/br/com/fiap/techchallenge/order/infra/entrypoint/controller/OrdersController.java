package br.com.fiap.techchallenge.order.infra.entrypoint.controller;

import br.com.fiap.techchallenge.order.application.usecase.order.CreateOrderUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.IsPaidUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderRequestDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.mapper.OrderMapper;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.openapi.OrderControllerOpenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
public class OrdersController implements OrderControllerOpenApi {

    private final CreateOrderUseCase createOrderUseCase;
    private final IsPaidUseCase isPaidUseCase;
    private final OrderMapper mapper;

    public OrdersController(CreateOrderUseCase createOrderUseCase,
                            IsPaidUseCase isPaidUseCase,
                            OrderMapper mapper) {
        this.createOrderUseCase = createOrderUseCase;
        this.isPaidUseCase = isPaidUseCase;
        this.mapper = mapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<CreateOrderResponseDTO> create(CreateOrderRequestDTO orderRequest){
        var order = createOrderUseCase.create(mapper.toCreateOrder(orderRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateOrderResponseDTO(order));
    }

    @Override
    @GetMapping("/{id}/paid-status")
    public ResponseEntity<Boolean> isOrderPaid(@PathVariable("id") final UUID id) {
        var response = isPaidUseCase.isOrderPaid(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
