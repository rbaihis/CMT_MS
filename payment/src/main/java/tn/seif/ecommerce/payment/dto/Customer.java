package tn.seif.ecommerce.payment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Builder
@Getter
@Setter
public class Customer {

    private String id;
    @NotNull(message = "firstName is mandatory")
    private String firstName;
    @NotNull(message = "lastName is mandatory")
    private String lastName;
    @Email(message = "email entered not correctly formatted")
    @NotNull(message = "firstName is required")
    private String email;
}
