package com.sistemasactivos.apirest.account.resources.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class HTTPError {
    @Schema(description = "Code", minLength = 2, maxLength = 22)
    private String status_code;
    @Schema(description = "Status", minLength = 2, maxLength = 22)
    private String status;
    @Schema(description = "description", minLength = 1, maxLength = 100)
    private String message;
}
