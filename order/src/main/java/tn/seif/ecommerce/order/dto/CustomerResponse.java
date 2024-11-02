package tn.seif.ecommerce.order.dto;

import lombok.*;

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
