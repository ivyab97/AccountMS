package com.sistemasactivos.apirest.account.resources.exception;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Ivan Andres Brestt
 */

public class DataIntegrityViolationNoActiveEntityException extends DataIntegrityViolationException{

    public DataIntegrityViolationNoActiveEntityException(String msg) {
        super(msg);
    }

}
