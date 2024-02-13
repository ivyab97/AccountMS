package com.sistemasactivos.apirest.account.service;

import com.sistemasactivos.apirest.account.config.ModelMapperConfig;
import com.sistemasactivos.apirest.account.interfaces.BaseService;
import com.sistemasactivos.apirest.account.model.Account;
import com.sistemasactivos.apirest.account.model.Base;
import com.sistemasactivos.apirest.account.model.CreditCard;
import com.sistemasactivos.apirest.account.repository.BaseRepository;
import com.sistemasactivos.apirest.account.resources.DTO.AccountRequest;
import com.sistemasactivos.apirest.account.resources.DTO.BaseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.webjars.NotFoundException;


public abstract class BaseServiceImpl <E extends Base, D extends BaseDTO, ID extends Serializable> implements BaseService <E, D, ID>{
    
    protected BaseRepository<E, ID > baseRepository;
    
    public BaseServiceImpl (BaseRepository<E, ID > baseRepository){
        this.baseRepository= baseRepository;
    }
    
    @Override
    public E findById(ID id) throws Exception {
        
        try {
            if(id==null || id.equals(0L)){
                throw new BadRequestException ("La solicitud es inválida, verificar el ID ingresado.");
            }
            
            Optional<E> entityOptional = baseRepository.findById(id);
            return entityOptional.get();
        
        }
         catch(BadRequestException e){
            throw new BadRequestException ("La solicitud es inválida o contiene datos incorrectos.");
        }
        catch(NoSuchElementException e){
            throw new NotFoundException ("El recurso solicitado no fue encontrado.");
        }
        catch (Exception e){
            throw new Exception ("Ocurrió un error interno en el servidor. Por favor, inténtalo de nuevo más tarde.");
        }
        
    }
    
    @Override
    public E findByIdActive(ID id) throws Exception {
        try {
                if(id==null || id.equals(0L)){
                    throw new BadRequestException ("La solicitud es inválida, verificar el ID ingresado.");
                }

                Optional<E> entityOptional = baseRepository.findById(id);

                if (entityOptional.get().getActive()) {
                    return entityOptional.get();
                } else{
                    throw new NotFoundException("");
                }
        
            }
             catch(BadRequestException e){
                throw new BadRequestException ("La solicitud es inválida o contiene datos incorrectos.");
            }
            catch(NoSuchElementException | NotFoundException e){
                throw new NotFoundException ("El recurso solicitado no fue encontrado.");
            }
            catch (Exception e){
                throw new Exception ("Ocurrió un error interno en el servidor. Por favor, inténtalo de nuevo más tarde.");
            }
    }
    
    
    @Override
    public Page<E> findAllByStatusEquals(Boolean status, Pageable pageable) throws Exception {
        try{
            
            if (status == null) {
            return baseRepository.findAll(pageable); // Trae todos los elementos
            } else {
                return baseRepository.findByActiveEquals(status, pageable); // Trae según el estado
            }
            
        }catch (Exception e){
            throw new Exception ("Ocurrió un error interno en el servidor. Por favor, inténtelo de nuevo más tarde.");
        }
    }

    
    
    @Transactional
    @Override
    public E save(D entity) throws Exception {
        
        try {
            Base entityMapper = ModelMapperConfig.modelMapper().map((entity), createEntityInstance(entity));
            
            entityMapper = baseRepository.save((E)entityMapper);
            return (E)entityMapper;
        }

        catch(ValidationException e){
            throw new BadRequestException ("La solicitud es inválida o contiene datos incorrectos.");
        }
        catch(DataIntegrityViolationException  e){
            if (e.getRootCause() instanceof SQLException && ((SQLException) e.getRootCause()).getErrorCode() == 1048) {
                throw new BadRequestException("La solicitud es inválida, uno o varios campos son nulos.");
            } else{
                throw new DataIntegrityViolationException ("La solicitud es inválida, existe un registro con el mismo identificador.");
            }
        }
        catch (Exception e){
            throw new Exception ("Ocurrió un error interno en el servidor. Por favor, inténtalo de nuevo más tarde.");
        }    
    }
    

    
    @Transactional
    @Override
    public boolean softDelete(ID id) throws Exception {
        try {
            if(id==null || id.equals(0L)){
                throw new BadRequestException();
            }

            if(baseRepository.existsById(id)){
                Optional<E> entity = baseRepository.findById(id);
                
                if (entity.get().getActive()) {
                    entity.get().setActive(false);
                    baseRepository.save(entity.get());
                    return true;
                    
                } else{
                    throw new NotFoundException("");
                }
            }
            else {
                throw new NotFoundException("");
            }
        }
        catch(BadRequestException e){
            throw new BadRequestException ("La solicitud es inválida o contiene datos incorrectos.");
        }
        catch(NotFoundException e){
            throw new NotFoundException ("El recurso solicitado no fue encontrado.");
        }
        catch (Exception e){
            throw new Exception ("Ocurrió un error interno en el servidor. Por favor, inténtalo de nuevo más tarde.");
        }
    }
    
    
    //Se deben cargar los models que se usan en esta implementacion generica
    private Class<? extends Base> createEntityInstance(D request) {
    if (request.getClass() == AccountRequest.class) {
        return Account.class;
    }else {
            return CreditCard.class;
      }
    }
    
}
