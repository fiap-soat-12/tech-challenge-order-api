package br.com.fiap.techchallenge.order.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.PaymentAcceptDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PaymentAcceptConsumer {

    private final Map<UUID, CompletableFuture<PaymentAcceptDTO>> responseMap = new ConcurrentHashMap<>();

    public void registerResponseHandler(UUID orderId, CompletableFuture<PaymentAcceptDTO> future) {
        responseMap.put(orderId, future);
    }


    @SqsListener("${sqs.queue.order.payment.listener}")
    public void receiveMessage(PaymentAcceptDTO dto){
        var orderId = dto.orderId();
        var qrCode = dto.qrCode();

        CompletableFuture<PaymentAcceptDTO> future = responseMap.remove(orderId);
        if (future != null) {
            future.complete(dto);
        }
    }
}
