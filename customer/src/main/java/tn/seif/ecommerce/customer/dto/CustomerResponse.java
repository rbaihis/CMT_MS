package tn.seif.ecommerce.customer.dto;

import lombok.*;
import tn.seif.ecommerce.customer.entity.Address;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponse {

    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
