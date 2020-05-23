package com.bfm.asyncHttp.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bfm.asyncHttp.model.Order;
import com.bfm.asyncHttp.service.AsyncHttpService;

/**
 * @author rohsi
 *
 */
@RestController
@RequestMapping("/orders")
public class AsyncHttpController {
	
	@Autowired
	AsyncHttpService asyncHttpService;
	
	@Autowired
	TaskExecutor taskExecutor;
	
	@GetMapping("/{orderId}")
	public CompletableFuture<Order> getOrderById(@PathVariable("orderId") int orderId) {
		return CompletableFuture.supplyAsync(() -> {
			return asyncHttpService.getOrder(orderId);
		}, taskExecutor);
	}
	
	@GetMapping("/")
	public CompletableFuture<List<Order>> getOrders() {
		return CompletableFuture.supplyAsync(() -> {
			return asyncHttpService.getOrders();
		}, taskExecutor);
	}

}
