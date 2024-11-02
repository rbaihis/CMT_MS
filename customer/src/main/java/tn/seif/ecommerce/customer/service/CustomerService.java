package tn.seif.ecommerce.customer.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.customer.entity.Customer;
import tn.seif.ecommerce.customer.dto.CustomerRequest;
import tn.seif.ecommerce.customer.dto.CustomerResponse;
import tn.seif.ecommerce.customer.util.ICustomerMapper;
import tn.seif.ecommerce.customer.repository.CustomerRepository;
import tn.seif.ecommerce.exception.CustomerNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository repository;
    private final ICustomerMapper mapper;
    public CustomerService(CustomerRepository repository, ICustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public String createCustomer(CustomerRequest request) {
        return repository.save(mapper.requestToCustomerMapping(request)).getId();
    }
    //---------------------------
    @Override
    public void updateCustomer(CustomerRequest request) {
        Customer customer = getCustomerById(request.getId());

        repository.save(mergeCustomer(customer , request));
    }



    private Customer mergeCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.getFirstName()))
            customer.setFirstName(request.getFirstName());
        if(StringUtils.isNotBlank(request.getLastName()))
            customer.setLastName(request.getLastName());
        if(StringUtils.isNotBlank(request.getEmail()))
            customer.setEmail(request.getEmail());
        if(request.getAddress() != null)
            customer.setAddress(request.getAddress());

        return customer;
    }

    //--------------------------

    private List<Customer> getAllCustomer(){
        return repository.findAll();
    }

    @Override
    public List<CustomerResponse> getAllCustomerResponse() {
        return getAllCustomer().stream().map(mapper::customerToResponseMapping).collect(Collectors.toList());
    }
    //-------------------------
    private Customer getCustomerById(String id){
        return repository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException(
                        String.format("can not find Customer:: No customer found with id %s ", id)
                ));
    }
    @Override
    public CustomerResponse getCustomerResponse(String id) {
        return mapper.customerToResponseMapping(getCustomerById(id));
    }

    @Override
    public boolean customerExist(String id) {
        return repository.existsById(id);
    }

    //--------
    @Override
    public void deleteCustomer(String id) {
        if (!customerExist(id))
            return;

        repository.deleteById(id);
    }
}

