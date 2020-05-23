package com.bfm.asyncHttp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bfm.asyncHttp.model.Order;
import com.bfm.asyncHttp.model.OrderStatus;

import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rohsi
 *
 */
@Service
@Slf4j
public class AsyncHttpService {
	
	private static List<Order> orders;
	
	private static Map<Integer, Order> orderMap;
	
	static {
		orders = new ArrayList<>();
		ThreadLocalRandom.current().ints(50, 3457, 6789).forEach(val -> {
			orders.add(Order.builder()
					.orderId(val)
					.amount(100 + val)
					.quantity(10)
					.status(OrderStatus.NEW)
					.address("MyTestAddress-"+val)
					.build());
		});
		
		orderMap = orders.stream().collect(Collectors.toMap(Order::getOrderId, Function.identity()));
			
		log.info("Orders ready for provision : {}", orders.size());
	}
	
	public Order getOrder(int orderId) {
		return orderMap.get(orderId);
	}
	
	public List<Order> getOrders() {
		return orders;
	}

}
