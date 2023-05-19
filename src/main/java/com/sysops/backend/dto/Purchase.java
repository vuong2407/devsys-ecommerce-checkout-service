package com.sysops.backend.dto;

import com.sysops.backend.entity.Address;
import com.sysops.backend.entity.Customer;
import com.sysops.backend.entity.Order;
import com.sysops.backend.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
