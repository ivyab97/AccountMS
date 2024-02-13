package com.sistemasactivos.apirest.account.interfaces;

import com.sistemasactivos.apirest.account.model.Base;
import com.sistemasactivos.apirest.account.resources.DTO.BaseDTO;
import java.io.Serializable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BaseController <E extends Base, D extends BaseDTO, ID extends Serializable>{
    
    public ResponseEntity<?> getAllRecord(@RequestParam Boolean status, @RequestParam Integer page, @RequestParam Integer size);
    public ResponseEntity<?> getRecordById(@PathVariable ID id);
    public ResponseEntity<?> activate(@PathVariable ID id);
    
    public ResponseEntity<?> getRecordByActiveId(@PathVariable ID id);
    public ResponseEntity<?> save(@RequestBody D entity);
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody D entity);
    public ResponseEntity<?> delete(@PathVariable ID id);

    
}
