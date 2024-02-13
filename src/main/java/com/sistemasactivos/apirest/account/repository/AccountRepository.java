package com.sistemasactivos.apirest.account.repository;

import com.sistemasactivos.apirest.account.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account, Integer>{
    
    boolean existsByCbu(String cbu);
    
    Account findByCbu(String cbu);
    
}
