package com.sistemasactivos.apirest.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name="creditcard")
public class CreditCard extends Base{
    
    @Schema(description = "Description", example = "This is a description", minLength = 10, maxLength = 50)
    @Size(min = 10, max = 50)
    @Column(nullable = false, length = 50)
    private String description;
    
    @Schema(description = "Card Number", example = "1111111111111111", minLength = 16, maxLength = 16)
    @Size(min = 16, max = 16)
    @Column(unique = true, nullable = false, length = 16)
    private String cardNumber;
    
    @Schema(description = "Date the card was issued")
    @Column(nullable = false)
    private LocalDate cardIssueDate;
    
    @Schema(description = "Date the card will expire")
    @Column(nullable = false)
    private LocalDate cardExpirationDate;
  
}
