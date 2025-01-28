package br.com.fiap.techchallenge.order.application.exceptions;

public class InvalidStatusUpdateException extends RuntimeException {

	public InvalidStatusUpdateException(String message) {
		super(message);
	}

}
