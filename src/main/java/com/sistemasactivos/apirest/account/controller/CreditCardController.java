package com.sistemasactivos.apirest.account.controller;

import com.sistemasactivos.apirest.account.model.CreditCard;
import com.sistemasactivos.apirest.account.resources.DTO.CreditCardRequest;
import com.sistemasactivos.apirest.account.resources.exception.BusinessException;
import com.sistemasactivos.apirest.account.resources.exception.HTTPError;
import com.sistemasactivos.apirest.account.service.CreditCardServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.webjars.NotFoundException;


@RestController
@CrossOrigin(origins="*")
@RequestMapping(path="/api/v1/creditcards")
public class CreditCardController extends BaseControllerImpl<CreditCard, CreditCardRequest, CreditCardServiceImpl>{
    
    @Autowired
    private CreditCardServiceImpl creditCardService;
    
    @Override
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CreditCard.class)))
    public ResponseEntity<?> getRecordById(@PathVariable Integer id) {
        return super.getRecordById(id);
    }
    
    @Override
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CreditCard.class)))
    public ResponseEntity<?> getRecordByActiveId(@PathVariable Integer id) {
        return super.getRecordByActiveId(id);
    }
    
    @PostMapping("/{accountId}")
    @Operation(
        summary = "Dar de alta un registro con su respectiva información.",
        description = "Dar de alta un registro.",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CreditCard.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = HTTPError.class)))
        }
    )
    public ResponseEntity<?> save(@PathVariable Integer accountId, @Valid @RequestBody CreditCardRequest entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(creditCardService.addCreditCard(accountId, entity));
        }
        catch(BadRequestException e){
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch(NotFoundException e){
            throw new BusinessException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch(DataIntegrityViolationException e){
           throw new BusinessException(HttpStatus.CONFLICT, e.getMessage());
        }
        catch(Exception e){
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }    
    }
    
    @Override
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CreditCard.class)))
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody CreditCardRequest entity) {
        return super.update(id, entity);
    }
    
    @Override
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CreditCard.class)))
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        return super.activate(id);
    }
}
