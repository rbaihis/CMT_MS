package tn.seif.ecommerce.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.exception.IdempotencyException;
import tn.seif.ecommerce.external.api.customer.CustomerClient;
import tn.seif.ecommerce.order.dto.CustomerResponse;
import tn.seif.ecommerce.external.api.payment.PaymentClientService;
import tn.seif.ecommerce.order.dto.PaymentRequest;
import tn.seif.ecommerce.external.api.product.ProductClientService;
import tn.seif.ecommerce.order.dto.PurchaseResponse;
import tn.seif.ecommerce.exception.BusinessException;
import tn.seif.ecommerce.order.dto.OrderConfirmation;
import tn.seif.ecommerce.external.producer.notification.OrderPublisher;
import tn.seif.ecommerce.order.entity.Order;
import tn.seif.ecommerce.order.repository.OrderRepo;
import tn.seif.ecommerce.order.dto.OrderRequest;
import tn.seif.ecommerce.order.dto.OrderResponse;
import tn.seif.ecommerce.order.util.OrderMapper;
import tn.seif.ecommerce.orderline.dto.OrderLineRequest;
import tn.seif.ecommerce.orderline.service.OrderLineService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderIMP implements OrderService {
    //ms-api
    private final CustomerClient customerClient;
    private final ProductClientService productClient;
    private final PaymentClientService paymentClient;
    //ms-messageBroker
    private final OrderPublisher orderPublisher;
    //services
    private final OrderRepo repo;
    private final OrderLineService orderLineservice;
    private final OrderMapper orderMapper;


    /*
        // idempotency method ("relies on ids from clients(UUIDv4+id(optional)) saved as reference check if reference already exists")
    *   // create check user id is same as principal (and exist in db ())
        // (use this approach if immediate stock change is priority) create bookProducts also --------> // rollBack productBooking on failure
        // persist order  (transactional) (with status in progress) AsyncStyle
        // persist orderLines (transactional ) AsyncStyle
        // start payment process (sync)  ------> //rollback by (sync/async) inform productInventory ms to revert changes back
        // purchaseBooking //freeBooking
    * */

    @Override
    @Transactional
    public Long createOrder(OrderRequest request) {
        log.info("order  : "+request.toString());

        // idempotency method todo: front-client must generate UUIDv4 unique reference mandatory
        if(repo.existsByReference(request.getReference()))
            throw  new IdempotencyException("Order already created or in process (check your orders):: please ovoid refresh, this to prevent you from re ordering");


        // to implement idempotencyMethod(reference to check if it exists or not UUID from client received) method
        CustomerResponse customer = customerClient.findCustomerById(request.getCustomerId()).orElseThrow(()-> new BusinessException("Create Order Failed:: No Customer Exist"));
        //consistent methods ==> bookProduct or //rollback productBooking

        List<PurchaseResponse> productsPurchased = productClient.purchaseProduct(request.getProducts());

        Order order = repo.save(orderMapper.toOrder(request, productsPurchased));
        Long orderId= order.getId();

        log.info("printing oder object : order => "+order.toString());
        log.info("printing oder id : orderID => "+orderId);

        // creating order lines and link it to order already created
        for (PurchaseResponse product : productsPurchased) {
            orderLineservice.saveOrderLine(OrderLineRequest.builder()
                    .id(null)
                    .orderId(order.getId())
                    .productId(product.getId())
                    .quantity(product.getQuantity())
                    .unitPrice(product.getPrice())
                    .build());
        }

        Long paymentId =paymentClient.processPayment(
                PaymentRequest.builder()
                        .customer(customer)
                        .totalAmount(order.getTotalAmount().doubleValue())
                        .paymentMethod(order.getPaymentMethod())
                        .orderId(orderId)
                        .orderReference(order.getReference())
                        .build()
        );

        // send the order confirmation to notification ms via message.
        orderPublisher.sendOrderConfirmation(
                OrderConfirmation.builder()
                        .orderReference(order.getReference())
                        .paymentMethod(order.getPaymentMethod())
                        .paymentId(paymentId)
                        .totalAMount(order.getTotalAmount())
                        .products(productsPurchased)
                        .customer(customer)
                .build());

        return order.getId();
    }

    @Override
    // fixme: this should be paginated too bad if not
    public List<OrderResponse> getAllOrder() {
        return repo.findAll().stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse findById(Long id) {
        return repo.findById(id).map(orderMapper::toOrderResponse).orElseThrow(
                // todo exception
                ()->new BusinessException("order not found with the provided id")
        );
    }

    @Override
    public List<OrderResponse> findCustomerOrders(String idCustomer) {

        return repo.findAllByCustomerIdOrderByCreatedDate(idCustomer)
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }


}
