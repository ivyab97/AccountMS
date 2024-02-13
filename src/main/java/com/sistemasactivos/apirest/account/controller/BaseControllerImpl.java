package com.sistemasactivos.apirest.account.controller;

import com.sistemasactivos.apirest.account.interfaces.BaseController;
import com.sistemasactivos.apirest.account.model.Base;
import com.sistemasactivos.apirest.account.resources.DTO.BaseDTO;
import com.sistemasactivos.apirest.account.resources.exception.BusinessException;
import com.sistemasactivos.apirest.account.resources.exception.DataIntegrityViolationNoActiveEntityException;
import com.sistemasactivos.apirest.account.resources.exception.HTTPError;
import com.sistemasactivos.apirest.account.service.BaseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.sql.SQLException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.webjars.NotFoundException;


public abstract class BaseControllerImpl <E extends Base, D extends BaseDTO, S extends BaseServiceImpl<E, D, Integer>> implements BaseController<E, D, Integer>{
    
    @Autowired
    public S service;
    
    @Override
    @GetMapping("")
     @Operation(
        summary = "Obtener todos los registros.",
        description = "Obtiene todos los registros.",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = HTTPError.class)))
        }
    )
    public ResponseEntity<?> getAllRecord(
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try{
            PageRequest pageRequest = PageRequest.of(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(service.findAllByStatusEquals(status, pageRequest));
        }
        catch(Exception e){
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); 
        }
    }
    
    
    @Override
    @GetMapping("/{id}/admin")
    @Operation(
        summary = "Obtener un registro por ID.",
        description = "Obtiene un registro especifico dado su ID.",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = HTTPError.class)))
        }
    )
    public ResponseEntity<?> getRecordById(@PathVariable Integer id) {
        
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));  
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
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener un registro por ID.",
        description = "Obtiene un registro especifico dado su ID.",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = HTTPError.class)))
        }
    )
    public ResponseEntity<?> getRecordByActiveId(@PathVariable Integer id) {
        
        try {
        
            return ResponseEntity.status(HttpStatus.OK).body(service.findByIdActive(id));
        
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
    @Operation(
        summary = "Dar de alta un registro con su respectiva información.",
        description = "Dar de alta un registro.",
        responses = {
            @ApiResponse(responseCode = "201", ref = "createdAPI"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = HTTPError.class)))
        }
    )
    public ResponseEntity<?> save(@RequestBody D entity) {
        
        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
        }
        catch(BadRequestException e){
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch(DataIntegrityViolationException e){
           throw new BusinessException(HttpStatus.CONFLICT, e.getMessage());
        }
        catch(Exception e){
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @PutMapping("/{id}")
    @Operation(
        summary = "Modificar un registro por ID.",
        description = "Modificar información de un registro pasando como parametro su identificador.",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = HTTPError.class)))
        }
    )
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody D entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity));
        }
        catch(BadRequestException e){
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch(NotFoundException e){
            throw new BusinessException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch(DataIntegrityViolationNoActiveEntityException e){
            throw new BusinessException(HttpStatus.CONFLICT, e.getMessage());
        }
        catch(DataIntegrityViolationException  e){
            if (e.getRootCause() instanceof SQLException && ((SQLException) e.getRootCause()).getErrorCode() == 1048) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "La solicitud es inválida, uno o varios campos son nulos.");
            } else{
                throw new BusinessException(HttpStatus.CONFLICT, "Existe un registro con el mismo identificador.");
            }
        }
        catch(Exception e){
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }    
    }
    
    
    @PutMapping("/{id}/activate")
    @Operation(
        summary = "Dar de alta un recurso desactivado.",
        description = "Activar un recurso desactivado y todos sus registros asociadas.",
        responses = {
            @ApiResponse(responseCode = "200", ref = "noContentAPI"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = HTTPError.class)))
        }
    )
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.activate(id));
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
    @DeleteMapping("/{id}")
     @Operation(
        summary = "Eliminar un registro por ID.",
        description = "Eliminar un registro pasando como parametro su identificador.",
        responses = {
            @ApiResponse(responseCode = "204", ref = "noContentAPI"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = HTTPError.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = HTTPError.class)))
        }
    )
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.softDelete(id));
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
