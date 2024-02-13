package com.sistemasactivos.apirest.account.interfaces;

import com.sistemasactivos.apirest.account.model.CreditCard;
import com.sistemasactivos.apirest.account.resources.DTO.CreditCardRequest;


public interface CreditCardService extends BaseService<CreditCard, CreditCardRequest, Integer>{
    public CreditCard addCreditCard(Integer accountId, CreditCardRequest request) throws Exception;
}
