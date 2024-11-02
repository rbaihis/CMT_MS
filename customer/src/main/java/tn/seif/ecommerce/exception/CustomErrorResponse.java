package tn.seif.ecommerce.exception;


import lombok.*;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomErrorResponse {
    Map<String,String> errors;
}
