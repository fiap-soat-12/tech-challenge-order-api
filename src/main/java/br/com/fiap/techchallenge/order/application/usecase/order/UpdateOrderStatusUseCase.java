package br.com.fiap.techchallenge.order.application.usecase.order;

public interface UpdateOrderStatusUseCase {

	void updateStatusByPaymentDataId(String paymentDataId, String status);

	void updateOrderStatus();

}
