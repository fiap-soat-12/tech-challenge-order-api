package br.com.fiap.techchallenge.order.application.exceptions;

public class AlreadyInStatusException extends RuntimeException {

	public AlreadyInStatusException(String message) {
		super(message);
	}

}
