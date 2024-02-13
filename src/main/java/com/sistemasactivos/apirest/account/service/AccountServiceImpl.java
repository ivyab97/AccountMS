package com.sistemasactivos.apirest.account.service;

import com.sistemasactivos.apirest.account.interfaces.AccountService;
import com.sistemasactivos.apirest.account.model.Account;
import com.sistemasactivos.apirest.account.model.CreditCard;
import com.sistemasactivos.apirest.account.repository.AccountRepository;
import com.sistemasactivos.apirest.account.repository.BaseRepository;
import com.sistemasactivos.apirest.account.resources.DTO.AccountRequest;
import com.sistemasactivos.apirest.account.resources.exception.DataIntegrityViolationNoActiveEntityException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, AccountRequest, Integer> implements AccountService{

    @Autowired
    private AccountRepository accountRepository;
    
    public AccountServiceImpl(BaseRepository<Account, Integer> baseRepository) {
        super(baseRepository);
    }
    
    @Override
    public Account findByIdActive(Integer id) throws Exception{
        try {
            Account account = super.findByIdActive(id);
            
            account.getCreditCards().removeIf(creditCard -> creditCard.getActive() == false); //retorno solo los activos
            
            return account;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Page<Account> findAllByStatusEquals(Boolean status, Pageable pageable) throws Exception{
        try {
                Page<Account> accountsPage = super.findAllByStatusEquals(status, pageable);
            
                if (status == null || !status) {
                    return accountsPage;
                } else {
                    for (Account account : accountsPage) {
                        account.getCreditCards().removeIf(creditCard -> creditCard.getActive() == false);
                    }
                    return accountsPage;
                }            
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }        
    }
    
    
    @Transactional
    @Override
    public Account update (Integer id, AccountRequest request) throws Exception{
        try {
            Account account = super.findById(id);
            
            if (account.getActive()) {
                account.setCbu(request.getCbu());
                account.setAlias(request.getAlias());
                account.setBank(request.getBankName());
                account.setCustomerId(request.getCustomerId());

                account = baseRepository.save(account);
            } else {
                throw new DataIntegrityViolationNoActiveEntityException("La cuenta no se encuentra activada.");
            }
            account.getCreditCards().removeIf(creditCard -> creditCard.getActive() == false);
            return account;
            
        } catch (Exception e){
            throw e;
        }
    }
    
    
    @Transactional
    @Override
    public boolean softDelete(Integer id) throws Exception{
        try {
            
            if (super.softDelete(id)) { //Si se ejecuta el softDelte, hago las eliminacion logica de las cards relacionadas
                Account account = super.findById(id);
                for (CreditCard creditCard : account.getCreditCards()) {
                    creditCard.setActive(false);
                }
            } else {
                return false;
            }
        
        return true;
            
        } catch (Exception e){
            throw e;
        }
    }
    
    @Transactional
    @Override
    public Account activate(Integer id) throws Exception{
        try {
                Account account = super.findById(id);
                if (!account.getActive()) {
                    account.setActive(true);
                    
                    for (CreditCard creditCard : account.getCreditCards()) {
                        creditCard.setActive(true);
                    }
                } else {
                    throw new DataIntegrityViolationException("El recurso solicitado ya se encuentra activo.");
                }
                return account;
                
        } catch (Exception e){
            throw e;
        }
    }

}
