package tn.seif.ecommerce.customer.util;

import org.springframework.stereotype.Service;
import tn.seif.ecommerce.customer.entity.Customer;
import tn.seif.ecommerce.customer.dto.CustomerRequest;
import tn.seif.ecommerce.customer.dto.CustomerResponse;

@Service
public class CustomerMapper implements ICustomerMapper {


    @Override
    public Customer requestToCustomerMapping(CustomerRequest request){
        if (request == null)
            return Customer.builder().build();

        return Customer.builder()
                .id(request.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .address(request.getAddress())
                .build();
    }


    @Override
    public CustomerResponse customerToResponseMapping(Customer customer){

        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .build();
    }






}
