package com.sistemasactivos.apirest.account.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI getApiInfo() {
        
        ApiResponse okAPI = new ApiResponse().content(
            new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, 
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                            new Example().value("{\"status_code\" : 200, \"status\" : \"Ok!\", \"message\" : \"La operación se completó con éxito.\"}")))
        );
        
        ApiResponse accountOkAPI = new ApiResponse()
            .description("Operación completada con éxito.")
            .content(new Content()
                .addMediaType(MediaType.APPLICATION_JSON_VALUE, new io.swagger.v3.oas.models.media.MediaType()
                    .schema(new Schema<>().$ref("#/components/schemas/Account")
                    )
                )
        );
        
         ApiResponse creditCardOkAPI= new ApiResponse()
            .description("Operación completada con éxito. ")
            .content(new Content()
                .addMediaType(MediaType.APPLICATION_JSON_VALUE, new io.swagger.v3.oas.models.media.MediaType()
                    .schema(new Schema<>().$ref("#/components/schemas/CreditCard")
                    )
                )
        );

        ApiResponse createdAPI = new ApiResponse().content(
            new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, 
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                            new Example().value("{\"status_code\" : 201, \"status\" : \"Created!\", \"message\" : \"El recurso se creó con éxito.\"}")))
        );
        
        ApiResponse noContentAPI = new ApiResponse().content(
            new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                            new Example().value("{\"status_code\" : 204, \"status\" : \"No content!\", \"message\" : \"La operación se completó con éxito.\"}")))
        );
        
  
        ApiResponse badRequestAPI = new ApiResponse().content(
            new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                            new Example().value("{\"status_code\" : 400, \"status\" : \"Bad request!\", \"message\" : \"La solicitud es inválida o contiene datos incorrectos.\"}")))
        );
        
        
        ApiResponse notFoundAPI = new ApiResponse().content(
            new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, 
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                            new Example().value("{\"status_code\" : 404, \"status\" : \"Not found!\", \"message\" : \"El recurso solicitado no fue encontrado.\"}")))
        );
        
        ApiResponse conflictAPI = new ApiResponse().content(
            new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, 
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                            new Example().value("{\"status_code\" : 409, \"status\" : \"Conflict!\", \"message\" : \"Solicitud en conflicto con otros registros.\"}")))
        );
        
        ApiResponse internalServerErrorAPI = new ApiResponse().content(
            new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, 
                    new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                            new Example().value("{\"status_code\" : 500, \"status\" : \"Internal server error!\", \"message\" : \"Ocurrió un error interno en el servidor. Por favor, inténtalo de nuevo más tarde.\"}")))
        );
        
        Components components = new Components();
        
        components.addResponses("okAPI", okAPI);
        components.addResponses("accountOkAPI", accountOkAPI);
        components.addResponses("creditCardOkAPI", creditCardOkAPI);
        components.addResponses("createdAPI", createdAPI);
        components.addResponses("noContentAPI", noContentAPI);
        components.addResponses("badRequestAPI", badRequestAPI);
        components.addResponses("notFoundAPI", notFoundAPI);
        components.addResponses("conflictAPI", conflictAPI);
        components.addResponses("internalServerErrorAPI", internalServerErrorAPI);


        return new OpenAPI()
            .components(components)
            .info(new Info()
                .title("MS Account")
                .version("v1")
                .description("Manejo de cuentas")
                .termsOfService("http://www.sistemasactivos.com/terminos")
                .contact(new Contact()
                    .name("Ivan")
                    .email("ivanbrestt@gmail.com")
                    .url("http://www.sistemasactivos.com/ivan"))
            );
    }
    
}

