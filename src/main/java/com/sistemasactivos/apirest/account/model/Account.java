package com.sistemasactivos.apirest.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "account")
public class Account extends Base {

    @Schema(description = "Account CBU", minLength = 22, maxLength = 22)
    @Column(unique = true, nullable = false, length = 22)
    @Size(min = 22, max = 22)
    private String cbu;
    
    @Schema(description = "Account Alias", minLength = 6, maxLength = 20)
    @Column(unique = true, nullable = true, length = 20)
    @Size(min = 6, max = 20)
    private String alias;
    
    @Schema(description = "Bank name", minLength = 10, maxLength = 40)    
    @Size(min = 10, max = 40)
    @Column(nullable = false, length = 40)
    private String bank;
    
    @Schema(description = "Customer Id", minLength = 1, maxLength = 9)    
    @Column(unique = true, nullable = false, length = 9)
    private Integer customerId;
    
    @Schema(description = "List of credit cards associated with this account")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "account_creditcard",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "creditcard_id")
    )
    private List<CreditCard> creditCards = new ArrayList();
}
