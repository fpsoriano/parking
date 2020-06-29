package com.fabricio.parking.service;

import static com.fabricio.parking.helpers.CustomerHelper.mockCustomerVo;
import static com.fabricio.parking.helpers.OrderHelper.mockOrderVo;
import static com.fabricio.parking.helpers.ParkingHelper.mockParkingVo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fabricio.parking.exceptions.BadRequestException;
import com.fabricio.parking.exceptions.Issue;
import com.fabricio.parking.exceptions.IssueEnum;
import com.fabricio.parking.exceptions.NotFoundException;
import com.fabricio.parking.repository.OrderRepository;
import com.fabricio.parking.repository.model.order.OrderModel;
import com.fabricio.parking.repository.model.parking.ParkingModel;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  public static final String ORDER_ID = "orderId";

  @InjectMocks
  private OrderService orderService;
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private CustomerService customerService;
  @Mock
  private ParkingService parkingService;

  @Test
  public void createOrder() {
    OrderModel customerModelExpected = mockOrderVo().toOrderModel();
    when(parkingService.findById(any(String.class))).thenReturn(mockParkingVo().toParkingModel());
    when(customerService.findById(any(String.class))).thenReturn(mockCustomerVo().toCustomerModel());
    when(orderRepository.save(any(OrderModel.class))).thenReturn(mockOrderVo().toOrderModel());
    OrderModel orderModel = orderService.create(customerModelExpected);
    verify(orderRepository, times(1)).save(any(OrderModel.class));
    assertOrderFields(customerModelExpected, orderModel);
  }

  @Test
  public void createOrderWithTheSameIdOfExistentOne_BadRequestException() {
    OrderModel orderModelMock = mockOrderVo().toOrderModel();
    when(orderRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(orderModelMock));
    Issue issueEnum = new Issue(IssueEnum.ORDER_ALREADY_EXISTS, orderModelMock.getId());
    BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
      orderService.create(orderModelMock);
    });
    assertEquals(badRequestException.getIssue().getMessage(),
        issueEnum.getMessage());
  }

  @Test
  public void createOrderOfNonexistentPlateCar_BadRequestException() {
    OrderModel orderModelMock = mockOrderVo().toOrderModel();
    orderModelMock.setPlateCar("test");
    when(parkingService.findById(any(String.class))).thenReturn(mockParkingVo().toParkingModel());
    when(customerService.findById(any(String.class))).thenReturn(mockCustomerVo().toCustomerModel());
    Issue issueEnum = new Issue(IssueEnum.PLATE_CAR_NOT_FOUND_FOR_CUSTOMER, orderModelMock.getPlateCar(), orderModelMock.getCustomerId());
    NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
      orderService.create(orderModelMock);
    });
    assertEquals(notFoundException.getIssue().getMessage(),
        issueEnum.getMessage());
  }

  @Test
  public void createOrderWithNoAvailableSlots_BadRequestException() {
    ParkingModel parkingModel = mockParkingVo().toParkingModel();
    parkingModel.getSlots().get(0).setNumberOfAvailableSlots(0);
    when(parkingService.findById(any(String.class))).thenReturn(parkingModel);
    when(customerService.findById(any(String.class))).thenReturn(mockCustomerVo().toCustomerModel());
    Issue issueEnum = new Issue(IssueEnum.NO_AVAILABLE_SLOT, parkingModel.getSlots().get(0).getType());
    BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
      orderService.create(mockOrderVo().toOrderModel());
    });
    assertEquals(badRequestException.getIssue().getMessage(),
        issueEnum.getMessage());
  }

  @Test
  public void updateOrder() {
    OrderModel orderModelMock = mockOrderVo().toOrderModel();
    when(parkingService.findById(any(String.class))).thenReturn(mockParkingVo().toParkingModel());
    when(customerService.findById(any(String.class))).thenReturn(mockCustomerVo().toCustomerModel());
    when(orderRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(mockOrderVo().toOrderModel()));
    when(orderRepository.save(any(OrderModel.class))).thenReturn(mockOrderVo().toOrderModel());
    OrderModel orderModel = orderService.update(orderModelMock, ORDER_ID);
    verify(orderRepository, times(1)).save(any(OrderModel.class));
    assertOrderFields(orderModelMock, orderModel);
  }

  @Test
  public void updateCloseOrder_BadRequestException() {
    OrderModel orderModelMock = mockOrderVo().toOrderModel();
    orderModelMock.setStatus("CLOSED");
    when(orderRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(orderModelMock));
    Issue issueEnum = new Issue(IssueEnum.CLOSED_ORDER_CANNOT_BE_UPDATED, orderModelMock.getId());
    BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
      orderService.update(orderModelMock, ORDER_ID);
    });
    assertEquals(badRequestException.getIssue().getMessage(),
        issueEnum.getMessage());
  }

  @Test
  public void updateOrderOfNonexistentPlateCar_BadRequestException() {
    OrderModel orderModelMock = mockOrderVo().toOrderModel();
    orderModelMock.setPlateCar("test");
    when(parkingService.findById(any(String.class))).thenReturn(mockParkingVo().toParkingModel());
    when(customerService.findById(any(String.class))).thenReturn(mockCustomerVo().toCustomerModel());
    when(orderRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(mockOrderVo().toOrderModel()));
    Issue issueEnum = new Issue(IssueEnum.PLATE_CAR_NOT_FOUND_FOR_CUSTOMER, orderModelMock.getPlateCar(), orderModelMock.getCustomerId());
    NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
      orderService.update(orderModelMock, ORDER_ID);
    });
    assertEquals(notFoundException.getIssue().getMessage(),
        issueEnum.getMessage());
  }

  @Test
  public void closeOrder() {
    OrderModel orderModelMock = mockOrderVo().toOrderModel();
    when(parkingService.findById(any(String.class))).thenReturn(mockParkingVo().toParkingModel());
    when(orderRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(mockOrderVo().toOrderModel()));
    when(orderRepository.save(any(OrderModel.class))).thenReturn(mockOrderVo().toOrderModel());
    OrderModel orderModel = orderService.closeOrder(ORDER_ID, 1592953200000L);
    verify(orderRepository, times(1)).save(any(OrderModel.class));

    orderModelMock.setStatus("CLOSED");
    orderModelMock.setPrice(new BigDecimal("3.48"));
    assertOrderFields(orderModelMock, orderModel);
  }

  @Test
  public void CloseOrderWithInvalidDateCombination() {
    OrderModel orderModelMock = mockOrderVo().toOrderModel();
    orderModelMock.setEntryDate(OffsetDateTime.parse("2020-07-23T19:30:00.999Z"));
    when(orderRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(orderModelMock));
    Issue issueEnum = new Issue(IssueEnum.INVALID_DATE_COMBINATION);
    BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
      orderService.closeOrder(ORDER_ID, 1592953200000L);
    });
    assertEquals(badRequestException.getIssue().getMessage(),
        issueEnum.getMessage());
  }

  @Test
  public void findOrderById() {
    OrderModel customerModelExpected = mockOrderVo().toOrderModel();
    when(orderRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(mockOrderVo().toOrderModel()));
    OrderModel orderModel = orderService.findOrderById(ORDER_ID);
    assertOrderFields(customerModelExpected, orderModel);
  }

  @Test
  public void findOrderByIdNotFound() {
    when(orderRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
    assertThrows(NotFoundException.class, () -> {
      orderService.findOrderById(ORDER_ID);
    });
  }

  public void assertOrderFields(OrderModel orderModelExpected, OrderModel orderModelResponse){
    Assert.assertEquals(orderModelExpected.getId(), orderModelResponse.getId());
    Assert.assertEquals(orderModelExpected.getEndDate(), orderModelResponse.getEndDate());
    Assert.assertEquals(orderModelExpected.getEntryDate(), orderModelResponse.getEntryDate());
    Assert.assertEquals(orderModelExpected.getParkingId(), orderModelResponse.getParkingId());
    Assert.assertEquals(orderModelExpected.getPlateCar(), orderModelResponse.getPlateCar());
    Assert.assertEquals(orderModelExpected.getStatus(), orderModelResponse.getStatus());
    Assert.assertEquals(orderModelExpected.getPrice(), orderModelResponse.getPrice());
  }

}