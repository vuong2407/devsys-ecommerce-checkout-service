package com.sysops.backend.service;

import com.sysops.backend.dao.CustomerRepository;
import com.sysops.backend.dto.Purchase;
import com.sysops.backend.dto.PurchaseResponse;
import com.sysops.backend.entity.Customer;
import com.sysops.backend.entity.Order;
import com.sysops.backend.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve order info from purchase
        Order order = purchase.getOrder();

        // generate tracking number for order
        String orderTrackingNumber = UUID.randomUUID().toString();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with order items
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //populate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }
}
