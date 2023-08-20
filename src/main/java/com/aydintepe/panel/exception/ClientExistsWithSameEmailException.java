package com.aydintepe.panel.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ClientExistsWithSameEmailException extends ConstraintViolationException {
    public ClientExistsWithSameEmailException() {
        super("Bu mail adresi ile kullanıcı vardır",null);
    }
}
