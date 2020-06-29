package com.fabricio.parking.controller;

import static com.fabricio.parking.helpers.CustomerHelper.mockCustomerVo;
import static com.fabricio.parking.helpers.OrderHelper.mockOrderVo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fabricio.parking.repository.model.customer.CustomerModel;
import com.fabricio.parking.repository.model.order.OrderModel;
import com.fabricio.parking.service.CustomerService;
import com.fabricio.parking.service.OrderService;
import com.fabricio.parking.vo.customer.CustomerVo;
import com.fabricio.parking.vo.order.OrderVo;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

  @InjectMocks
  private OrderController orderController;

  @Mock
  private OrderService orderService;

  @Test
  public void addOrder() {
    OrderVo orderVoRequest = mockOrderVo();
    when(orderService.create(any(OrderModel.class))).thenReturn(mockOrderVo().toOrderModel());
    ResponseEntity<OrderVo> orderVoResponseEntity = orderController.saveOrder(orderVoRequest);
    verify(orderService, times(1)).create(any(OrderModel.class));
    assertOrderFields(orderVoRequest, orderVoResponseEntity.getBody());
  }

  @Test
  public void updateOrder() {
    OrderVo orderVoRequest = mockOrderVo();
    when(orderService.update(any(OrderModel.class), any(String.class))).thenReturn(mockOrderVo().toOrderModel());
    ResponseEntity<OrderVo> orderVoResponseEntity = orderController.updateOrder(orderVoRequest, orderVoRequest.getId());
    verify(orderService, times(1)).update(any(OrderModel.class), any(String.class));
    assertOrderFields(orderVoRequest, orderVoResponseEntity.getBody());
  }

  @Test
  public void findOrderById() {
    OrderVo orderVoExpected = mockOrderVo();
    when(orderService.findOrderById(any(String.class))).thenReturn(mockOrderVo().toOrderModel());
    OrderVo orderVo = orderController.getOrderById(orderVoExpected.getId());
    verify(orderService, times(1)).findOrderById(orderVoExpected.getId());
    assertOrderFields(orderVoExpected, orderVo);
  }

  @Test
  public void closeOrder() {
    when(orderService.closeOrder(any(String.class), any(Long.class))).thenReturn(mockOrderVo().toOrderModel());
    orderController.closeOrder("orderId", 1592953200000L);
    verify(orderService, times(1)).closeOrder(any(String.class), any(Long.class));
  }

  public void assertOrderFields(OrderVo orderVoExpected, OrderVo orderVoResponse){
    Assert.assertEquals(orderVoExpected.getId(), orderVoResponse.getId());
    Assert.assertEquals(orderVoExpected.getEndDate(), orderVoResponse.getEndDate());
    Assert.assertEquals(orderVoExpected.getEntryDate(), orderVoResponse.getEntryDate());
    Assert.assertEquals(orderVoExpected.getParkingId(), orderVoResponse.getParkingId());
    Assert.assertEquals(orderVoExpected.getPlateCar(), orderVoResponse.getPlateCar());
    Assert.assertEquals(orderVoExpected.getStatus(), orderVoResponse.getStatus());
    Assert.assertEquals(orderVoExpected.getPrice(), orderVoResponse.getPrice());
  }


}