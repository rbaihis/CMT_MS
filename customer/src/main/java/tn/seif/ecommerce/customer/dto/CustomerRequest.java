package tn.seif.ecommerce.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tn.seif.ecommerce.customer.entity.Address;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class CustomerRequest {

    private String id;
    @NotNull(message = "customer firstName is required")
    private String firstName;
    @NotNull(message = "customer lastName is required")
    private String lastName;
    @Email(message = "customer email is not valid")
    private String email;
//    already validated using @Validated on the pojoclass
    private Address address;
}
