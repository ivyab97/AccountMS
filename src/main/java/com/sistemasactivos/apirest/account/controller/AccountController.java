package com.sistemasactivos.apirest.account.controller;

import com.sistemasactivos.apirest.account.model.Account;
import com.sistemasactivos.apirest.account.resources.DTO.AccountRequest;
import com.sistemasactivos.apirest.account.resources.exception.BusinessException;
import com.sistemasactivos.apirest.account.service.AccountServiceImpl;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.webjars.NotFoundException;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(path="/api/v1/accounts")
public class AccountController extends BaseControllerImpl<Account, AccountRequest, AccountServiceImpl>{
    
    @Autowired
    private AccountServiceImpl accountService;
    
    @Override
    public ResponseEntity<?> getAllRecord(
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try{
            PageRequest pageRequest = PageRequest.of(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(accountService.findAllByStatusEquals(status, pageRequest));
        }
        catch(Exception e){
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); 
        }
    }
    
    @Override
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Account.class)))
    public ResponseEntity<?> getRecordById(@PathVariable Integer id) {
        return super.getRecordById(id);
    }
    
    @Override
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Account.class)))
    public ResponseEntity<?> getRecordByActiveId(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.findByIdActive(id));
        }
        catch(BadRequestException e){
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage()); 
        }
        catch(NotFoundException e){
            throw new BusinessException(HttpStatus.NOT_FOUND, e.getMessage()); 
        }
        catch(Exception e){
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); 
        }
    }
    
    
    @Override
    @PostMapping("")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = Account.class)))
    public ResponseEntity<?> save(@Valid @RequestBody AccountRequest entity) {
        return super.save(entity);
    }
    
    @Override
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Account.class)))
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody AccountRequest entity) {
        return super.update(id, entity);
    }
    
    
    @Override
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Account.class)))
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        return super.activate(id);
    }
    
    
    @Override
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(accountService.softDelete(id));
        }
        catch(BadRequestException e){
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch(NotFoundException e){
            throw new BusinessException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch(Exception e){
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        
    }

}
