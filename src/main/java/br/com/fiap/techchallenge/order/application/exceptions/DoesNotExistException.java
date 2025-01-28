package br.com.fiap.techchallenge.order.application.exceptions;

public class DoesNotExistException extends RuntimeException {

	public DoesNotExistException(String message) {
		super(message);
	}

}
