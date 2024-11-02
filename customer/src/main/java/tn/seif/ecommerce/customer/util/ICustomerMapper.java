package tn.seif.ecommerce.customer.util;

import tn.seif.ecommerce.customer.entity.Customer;
import tn.seif.ecommerce.customer.dto.CustomerRequest;
import tn.seif.ecommerce.customer.dto.CustomerResponse;


public interface ICustomerMapper {

    public Customer requestToCustomerMapping(CustomerRequest request);
    public CustomerResponse customerToResponseMapping(Customer customer);



}
