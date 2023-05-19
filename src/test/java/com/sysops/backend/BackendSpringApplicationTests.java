package com.sysops.backend;

import com.sysops.backend.dao.CustomerRepository;
import com.sysops.backend.dto.Purchase;
import com.sysops.backend.dto.PurchaseResponse;
import com.sysops.backend.entity.Address;
import com.sysops.backend.entity.Customer;
import com.sysops.backend.entity.Order;
import com.sysops.backend.entity.OrderItem;
import com.sysops.backend.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BackendSpringApplicationTests {

	@MockBean
	private CustomerRepository customerRepository;

	@Autowired
	private CheckoutService checkoutService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testPlaceOrder() {
		// Create a mock Purchase object
		Purchase purchase = createMockPurchase();
		System.out.println(purchase);
		// Mock behavior of the customerRepository.save() method
		when(customerRepository.save(any(Customer.class))).thenReturn(purchase.getCustomer());

		// Call the placeOrder() method
		PurchaseResponse response = checkoutService.placeOrder(purchase);

		// Verify that the customerRepository.save() method is called with the expected argument
		verify(customerRepository).save(any(Customer.class));

		// Verify the response
		assertEquals(purchase.getOrder().getOrderTrackingNumber(), response.getOrderTrackingNumber());
	}

	private Purchase createMockPurchase() {
		Purchase purchase = new Purchase();

		// Create a mock Order object
		Order order = new Order();
		order.setOrderTrackingNumber(UUID.randomUUID().toString());

		// create a mock billingAddress and shippingAddress object
		Address billingAndShippingAddress = new Address();
		billingAndShippingAddress.setStreet("DT");
		billingAndShippingAddress.setCity("HCM");
		billingAndShippingAddress.setState("Acre");
		billingAndShippingAddress.setCountry("Viet Nam");
		billingAndShippingAddress.setZipCode("147247");
		order.setShippingAddress(billingAndShippingAddress);
		order.setBillingAddress(billingAndShippingAddress);

		// Create a mock OrderItem object
		OrderItem orderItem = new OrderItem();
		// Set properties of the order item
		orderItem.setImageUrl("ssets/images/products/coffeemugs/coffeemug-luv2code-1000.png");
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(BigDecimal.valueOf(20.22));
		orderItem.setProductId(3L);

		// Create a mock Customer object
		Customer customer = new Customer();
		// Set properties of the customer
		customer.setLastName("Vuong");
		customer.setFirstName("Nguyen");
		customer.setEmail("vanvuong24072001@gmail.com");

		// Set relationships between entities
		Set<OrderItem> orderItems = new HashSet<>();
		orderItems.add(orderItem);
		purchase.setOrder(order);
		purchase.setCustomer(customer);
		purchase.setShippingAddress(billingAndShippingAddress);
		purchase.setBillingAddress(billingAndShippingAddress);
		purchase.setOrderItems(orderItems);

		return purchase;
	}
}
