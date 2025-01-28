package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.application.usecase.order.UpdateOrderStatusUseCase;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.PaymentResponseDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentResponseConsumer {

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public PaymentResponseConsumer(UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @SqsListener("${sqs.queue.order.payment.listener}")
    public void receiveMessage(PaymentResponseDTO dto){
        updateOrderStatusUseCase.updateStatusByPaymentDataId(dto.paymentDataId(), dto.status());
    }
}
