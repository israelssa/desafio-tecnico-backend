package br.com.desafio.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException{
	
	private static final long serialVersionUID = 8244285423317210099L;

	public ResourceConflictException(String message) {
		super(message);
	}
}
