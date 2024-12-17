package com.backend.ecommerce.domain.exception.entidadeException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.backend.ecommerce.domain.exception.NegocioException;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntidadeDuplicadaException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public EntidadeDuplicadaException(String mensagem) {
		super(mensagem);
	}

}