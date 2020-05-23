/**
 * 
 */
package com.bfm.asyncHttp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.bfm.asyncHttp.controller.AsyncHttpController;
import com.bfm.asyncHttp.model.Order;
import com.bfm.asyncHttp.model.OrderStatus;
import com.bfm.asyncHttp.service.AsyncHttpService;

/**
 * @author rohsi
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AsyncHttpController.class)
public class AsyncHttpControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AsyncHttpService asyncHttpService;

	@Test
	public void testAsyncHttpController() throws Exception {
		Order ord = Order.builder()
				.orderId(1234)
				.quantity(100)
				.amount(2500)
				.address("test-address")
				.status(OrderStatus.NEW)
				.build();
		List<Order> orders = new ArrayList<>();
		orders.add(ord);
		
		when(asyncHttpService.getOrders()).thenReturn(orders);
		
		MvcResult mvcResult = mockMvc
				.perform(get("/"))
				.andExpect(request().asyncStarted())
				.andDo(MockMvcResultHandlers.log())
				.andReturn();

		mockMvc
		.perform(asyncDispatch(mvcResult))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
		.andExpect(content().json("{\"orderId\":\"1234\"}"));
	}

}
