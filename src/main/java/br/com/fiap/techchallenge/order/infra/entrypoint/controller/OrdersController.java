package br.com.fiap.techchallenge.order.infra.entrypoint.controller;

import br.com.fiap.techchallenge.order.application.usecase.order.CreateOrderUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.FindOrderStatusUseCase;
import br.com.fiap.techchallenge.order.application.usecase.order.IsPaidUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderRequestDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CreateOrderResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.OrderStatusResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.PaidRequestDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.mapper.OrderMapper;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.openapi.OrderControllerOpenApi;
import br.com.fiap.techchallenge.order.infra.gateway.producer.paid.PaidProducer;
import br.com.fiap.techchallenge.order.infra.gateway.producer.paid.dto.PaidDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
public class OrdersController implements OrderControllerOpenApi {

    private final CreateOrderUseCase createOrderUseCase;
    private final IsPaidUseCase isPaidUseCase;
    private final FindOrderStatusUseCase findOrderStatusUseCase;
    private final PaidProducer paidProducer;
    private final OrderMapper mapper;

    public OrdersController(CreateOrderUseCase createOrderUseCase,
                            IsPaidUseCase isPaidUseCase,
                            FindOrderStatusUseCase findOrderStatusUseCase,
                            PaidProducer paidProducer,
                            OrderMapper mapper) {
        this.createOrderUseCase = createOrderUseCase;
        this.isPaidUseCase = isPaidUseCase;
        this.findOrderStatusUseCase = findOrderStatusUseCase;
        this.paidProducer = paidProducer;
        this.mapper = mapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<CreateOrderResponseDTO> create(@RequestBody CreateOrderRequestDTO orderRequest) throws JsonProcessingException {
        var order = createOrderUseCase.create(mapper.toCreateOrder(orderRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateOrderResponseDTO(order));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrderStatusResponseDTO> findOrderStatus(@PathVariable("id") final UUID id){
        var response = findOrderStatusUseCase.findOrderStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(new OrderStatusResponseDTO(response));
    }

    @Override
    @GetMapping("/{id}/paid-status")
    public ResponseEntity<Boolean> isOrderPaid(@PathVariable("id") final UUID id) {
        var response = isPaidUseCase.isOrderPaid(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Void> paidOrder(@PathVariable("id") final UUID id, @RequestBody PaidRequestDTO dto) throws JsonProcessingException {
        paidProducer.sendToPaid(new PaidDTO(id, dto.isPaid()));
        return ResponseEntity.ok().build();
    }
}
