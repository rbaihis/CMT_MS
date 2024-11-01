package tn.seif.ecommerce.customer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.seif.ecommerce.customer.entity.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
