package com.sistemasactivos.apirest.account.config;

import com.sistemasactivos.apirest.account.model.Account;
import com.sistemasactivos.apirest.account.model.Base;
import com.sistemasactivos.apirest.account.resources.DTO.AccountRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Ivan Andres Brestt
 */

@Configuration
public class ModelMapperConfig {

    private static final ModelMapper modelMapper = createModelMapper();

    private static ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // Ignora el mapeo de customerId a Id en la clase base Base
        modelMapper.typeMap(AccountRequest.class, Base.class)
                   .addMappings(mapper -> mapper.skip(Base::setId));
        // Ignora el mpeo de ciertos Ids en las clases AccountRequest y Account
        modelMapper.typeMap(AccountRequest.class, Account.class)
                   .addMappings(mapper -> mapper.skip(AccountRequest::getCustomerId, Account::setId));
        
        return modelMapper;
    }

    @Bean
    public static ModelMapper modelMapper() {
        return modelMapper;
    }
}