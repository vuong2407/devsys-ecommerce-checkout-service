package com.sysops.backend.service;

import com.sysops.backend.dto.Purchase;
import com.sysops.backend.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
