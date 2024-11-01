package tn.seif.ecommerce.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.seif.ecommerce.payment.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment,Long> {

}
