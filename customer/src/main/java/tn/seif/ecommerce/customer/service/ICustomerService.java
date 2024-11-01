package tn.seif.ecommerce.customer.service;

import tn.seif.ecommerce.customer.dto.CustomerRequest;
import tn.seif.ecommerce.customer.dto.CustomerResponse;

import java.util.List;


public interface ICustomerService {
    String createCustomer(CustomerRequest request);

    void updateCustomer(CustomerRequest request);


    List<CustomerResponse> getAllCustomerResponse();


    CustomerResponse getCustomerResponse(String id);

    boolean customerExist(String id);

    void deleteCustomer(String id);
}
