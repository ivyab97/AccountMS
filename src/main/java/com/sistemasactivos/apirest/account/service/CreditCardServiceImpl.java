package com.sistemasactivos.apirest.account.service;

import com.sistemasactivos.apirest.account.interfaces.CreditCardService;
import com.sistemasactivos.apirest.account.model.Account;
import com.sistemasactivos.apirest.account.model.CreditCard;
import com.sistemasactivos.apirest.account.repository.AccountRepository;
import com.sistemasactivos.apirest.account.repository.BaseRepository;
import com.sistemasactivos.apirest.account.repository.CreditCardRepository;
import com.sistemasactivos.apirest.account.resources.DTO.CreditCardRequest;
import com.sistemasactivos.apirest.account.resources.exception.DataIntegrityViolationNoActiveEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.webjars.NotFoundException;

@Service
public class CreditCardServiceImpl extends BaseServiceImpl<CreditCard, CreditCardRequest, Integer> implements CreditCardService{
    
    @Autowired
    private AccountRepository accountRepository;  
    
    @Autowired
    private CreditCardRepository creditCardRepository;
    
    
    public CreditCardServiceImpl(BaseRepository<CreditCard, Integer> baseRepository) {
        super(baseRepository);
    }
    
    @Transactional
    @Override
    public CreditCard update(Integer id, CreditCardRequest request) throws Exception {
        try {
            if (request == null) {   //Si no se carga una entidad
                throw new BadRequestException ("La solicitud es inválida o contiene datos incorrectos.");
            }
           
            Account account = creditCardRepository.findAccountByCreditCardId(id);
            CreditCard creditCard = super.findById(id);
            
            if (account == null) {
                throw new NotFoundException ("El recurso solicitado no fue encontrado.");
            }
            else if (!account.getActive()|| !creditCard.getActive()) {
                throw new DataIntegrityViolationNoActiveEntityException("La tarjeta o la cuenta asociada no se encuentra activa.");
            } else {
                creditCard.setDescription(request.getDescription());
                creditCard.setCardNumber(request.getCardNumber());
                creditCard.setCardIssueDate(request.getCardIssueDate());
                creditCard.setCardExpirationDate(request.getCardExpirationDate());
                creditCard = creditCardRepository.save(creditCard);
            }
            return creditCard;
        }
        catch(ValidationException e){
            throw new BadRequestException ("La solicitud es inválida o contiene datos incorrectos.");
        }
        catch (Exception e){
            throw e;
        }
    }
    
    @Transactional
    @Override
    public CreditCard addCreditCard(Integer accountId, CreditCardRequest request) throws Exception {
        try {       
                Optional<Account> account = accountRepository.findById(accountId);
            
                if (account.get().getActive()) {
                    CreditCard creditCard = super.save(request);
                    account.get().getCreditCards().add(creditCard);
                    return creditCard;
                } else {
                    throw new DataIntegrityViolationException("La cuenta asociada no se encuentra activada.");
                }        
        }
        catch (NoSuchElementException e){
            throw new NotFoundException("La cuenta asociada al Id ingresado no ha sido encontrada.");
        }
        catch (Exception e){
            throw e;
        }
    }
    
    @Transactional
    @Override
    public CreditCard activate(Integer cardId) throws Exception{
        try {
                Account account = creditCardRepository.findAccountByCreditCardId(cardId);
                
                if (account == null){
                    throw new NotFoundException("No se ha encontrado la tarjeta con el ID ingresado.");
                }
                else if (!account.getActive()) {
                    throw new DataIntegrityViolationException("La cuenta asociada no se encuentra activada.");
                } else {
                    CreditCard creditCard = super.findById(cardId);
                    if (!creditCard.getActive()) {
                        creditCard.setActive(true);
                    } else {
                        throw new DataIntegrityViolationException("La tarjeta ya se encuentra activada.");
                    }
                    return creditCard;
                }  
        }
        catch (Exception e){
            throw e;
        }
    }
  
}
