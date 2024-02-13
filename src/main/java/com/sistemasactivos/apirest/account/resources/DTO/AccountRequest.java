package com.sistemasactivos.apirest.account.resources.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ivan Andres Brestt
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest extends BaseDTO{
    
    @Schema(description = "Account CBU", example = "1111111111111111111111", minLength = 22, maxLength = 22)
    @NotNull(message = "CBU no puede ser null.")
    @JsonProperty(required = true)
    @Size(min = 22, max = 22, message = "CBU debe tener 22 digitos.")
    private String cbu;
    
    @Schema(description = "Account Alias", example = "greenmoney999", minLength = 6, maxLength = 20)
    @NotNull(message = "Alias no puede ser null.")
    @JsonProperty(required = true)
    @Size(min = 6, max = 20, message = "Alias debe tener de 6 a 20 caracteres.")
    private String alias;
    
    @Schema(description = "Bank name", example = "Bank of China", minLength = 6, maxLength = 40)
    @NotNull(message = "BankName no puede ser null.")
    @JsonProperty(required = true)
    @Size(min = 6, max = 40, message = "BankName debe tener de 6 a 40 caracteres.")    
    private String bankName;
    
    
    @Schema(description = "Customer Id", example = "10", minLength = 1, maxLength = 9)   
    @NotNull(message = "CustomerId no puede ser null.")
    @JsonProperty(required = true)
    @Digits(fraction = 0, integer = 9, message = "CustomerId debe tener de 1 a 9 digitos.")    
    private Integer customerId;
    
    @Schema(description = "List of credit cards associated with this account")
    @Valid
    private List<CreditCardRequest> creditCards;
    
}
