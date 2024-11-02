package tn.seif.ecommerce.exception;


import lombok.*;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//
public class ProductErrorResponse {
    Map<String,String> errors;
}
