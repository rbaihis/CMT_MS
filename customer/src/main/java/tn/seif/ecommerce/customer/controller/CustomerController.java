package tn.seif.ecommerce.customer.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.seif.ecommerce.customer.dto.CustomerRequest;
import tn.seif.ecommerce.customer.dto.CustomerResponse;
import tn.seif.ecommerce.customer.service.ICustomerService;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController {
    private final ICustomerService service;

    public CustomerController(ICustomerService service) {
        this.service = service;
    }
    //---------
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid CustomerRequest request){

        return ResponseEntity.ok().body(service.createCustomer(request));
    }
    //---------
    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request){

        service.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }
    //---------
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){

        return ResponseEntity.ok().body(
                service.getAllCustomerResponse()
        );
    }
    //----------
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String id){

        return ResponseEntity.ok().body(
                service.getCustomerResponse(id)
        );
    }
    //-----
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> isCustomerExist(@PathVariable String id){

        return ResponseEntity.ok().body(
                service.customerExist(id)
        );
    }

    //-----

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id){
        service.deleteCustomer(id);
        return ResponseEntity.ok().build( );
    }


}
