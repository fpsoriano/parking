package com.fabricio.parking.controller;

import com.fabricio.parking.repository.model.order.OrderModel;
import com.fabricio.parking.service.OrderService;
import com.fabricio.parking.vo.order.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = {"Order API"})
public class OrderController {

  private final OrderService orderService;
  public OrderController(OrderService orderService) {this.orderService = orderService;}

  @ApiOperation(
      value = "Create an Order",
      notes = "It is necessary to create an order to register some information that will be used to calculate and charge the client")
  @PostMapping("/order")
  public ResponseEntity saveOrder(@ApiParam(value = "Order object", required = true) @Valid @RequestBody final OrderVo orderRequest) {
    log.info("Request : {}", orderRequest);
    OrderVo orderVo = orderService.create(orderRequest.toOrderModel()).toOrderVo();
    return new ResponseEntity<>(orderVo, HttpStatus.CREATED);
  }

  @ApiOperation(
      value = "Update an Order")
  @PutMapping("/order/{orderId}")
  public ResponseEntity<OrderVo> updateOrder(
      @ApiParam(value = "Order object", required = true) @Valid @RequestBody final OrderVo orderRequest,
      @ApiParam(value = "Order id", required = true) @PathVariable final String orderId
  ) {
    log.info("Order Id : {}", orderId);
    OrderVo orderVo = orderService.update(orderRequest.toOrderModel(), orderId).toOrderVo();
    return new ResponseEntity<>(orderVo, HttpStatus.OK);
  }

  @PutMapping("/order/{orderId}/close")
  public ResponseEntity<OrderVo> closeOrder(
      @ApiParam(value = "Order id", required = true) @PathVariable final String orderId,
      @ApiParam(value = "Order id. Id not informed the application will get the current date/time") @RequestParam final Long timestamp
      ) {
    log.info("Order Id : {}", orderId);
    OrderVo orderVo = orderService.closeOrder(orderId, timestamp).toOrderVo();
    return new ResponseEntity<>(orderVo, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Get order by id",
      notes = "Retrieves order by id to check all the information")
  @GetMapping("/order/{orderId}")
  public OrderVo getOrderById(@ApiParam(value = "Order id", required = true) @PathVariable final String orderId) {

    return orderService.findOrderById(orderId).toOrderVo();
  }
}
