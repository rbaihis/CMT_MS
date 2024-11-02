package tn.seif.ecommerce.external.api.product;

import tn.seif.ecommerce.order.dto.PurchaseRequest;
import tn.seif.ecommerce.order.dto.PurchaseResponse;

import java.util.List;


public interface ProductClientService {


    List<PurchaseResponse> purchaseProduct(List<PurchaseRequest> requestBody);
}
