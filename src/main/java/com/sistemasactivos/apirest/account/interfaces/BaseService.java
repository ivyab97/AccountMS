package com.sistemasactivos.apirest.account.interfaces;

import com.sistemasactivos.apirest.account.model.Base;
import com.sistemasactivos.apirest.account.resources.DTO.BaseDTO;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService <E extends Base, D extends BaseDTO, ID extends Serializable>{
    
    public Page<E> findAllByStatusEquals(Boolean status, Pageable pageable) throws Exception;
    public E findByIdActive(ID id)throws Exception; // Solo busca los activos
    public E findById(ID id) throws Exception;  // Busca todos los registros, activos o no
    public E save (D entity) throws Exception;
    public E update(ID id, D entity) throws Exception;
    public boolean softDelete(ID id) throws Exception;
    public E activate(ID id) throws Exception;  //Da de alta un recurso
    
}
