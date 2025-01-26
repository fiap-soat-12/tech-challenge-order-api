package br.com.fiap.techchallenge.order.application.exceptions;

public class AlreadyExistsException extends RuntimeException {

	public AlreadyExistsException(String message) {
		super(message);
	}

}
