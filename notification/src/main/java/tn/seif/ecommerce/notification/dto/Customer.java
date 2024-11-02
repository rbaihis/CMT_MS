package tn.seif.ecommerce.notification.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

}
