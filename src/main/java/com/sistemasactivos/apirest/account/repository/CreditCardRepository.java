package com.sistemasactivos.apirest.account.repository;

import com.sistemasactivos.apirest.account.model.Account;
import com.sistemasactivos.apirest.account.model.CreditCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends BaseRepository<CreditCard, Integer>{
    
    @Query("SELECT a FROM Account a JOIN a.creditCards c WHERE c.id = :creditCardId")
    Account findAccountByCreditCardId(@Param("creditCardId") Integer creditCardId);
}
