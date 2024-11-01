package tn.seif.ecommerce.exception.dto;


import lombok.*;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductErrorResponse {
    Map<String,String> errors;
}
