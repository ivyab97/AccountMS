package com.sistemasactivos.apirest.account.repository;

import com.sistemasactivos.apirest.account.model.Base;
import org.springframework.data.domain.Pageable;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BaseRepository <E extends Base, ID extends Serializable> extends JpaRepository<E, ID>{
    
    Page<E> findByActiveEquals(Boolean status, Pageable pageable);

}
