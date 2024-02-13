package com.sistemasactivos.apirest.account.resources.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Ivan Andres Brestt
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardRequest extends BaseDTO{
    
    @Schema(description = "Description", example = "This is a description", minLength = 10, maxLength = 50)
    @NotNull(message = "Description no puede ser null.")
    @JsonProperty(required = true)
    @Size(min = 10, max = 50, message = "Description debe tener de 10 a 50 caracteres.")
    private String description;
    
    @Schema(description = "Card Number", example = "1111111111111111", minLength = 16, maxLength = 16)
    @NotNull(message = "CardNumber no puede ser null.")
    @JsonProperty(required = true)
    @Size(min = 16, max = 16, message = "CardNumber deber ser de 16 digitos.")
    private String cardNumber;
    
    @Schema(description = "Date the card was issued")
    @NotNull(message = "CardIssueDate no puede ser null.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(required = true)
    private LocalDate cardIssueDate;
    
    @Schema(description = "Date the card will expire")
    @NotNull(message = "CardExpirationDate no puede ser null.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(required = true)
    private LocalDate cardExpirationDate;
    
}
