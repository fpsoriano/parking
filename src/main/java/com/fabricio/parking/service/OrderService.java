package com.fabricio.parking.service;

import com.fabricio.parking.exceptions.BadRequestException;
import com.fabricio.parking.exceptions.IssueEnum;
import com.fabricio.parking.exceptions.NotFoundException;
import com.fabricio.parking.helper.OffsetDateTimeHelper;
import com.fabricio.parking.helper.OrderHelper;
import com.fabricio.parking.repository.OrderRepository;
import com.fabricio.parking.repository.model.customer.CarModel;
import com.fabricio.parking.repository.model.customer.CustomerModel;
import com.fabricio.parking.repository.model.order.OrderModel;
import com.fabricio.parking.repository.model.parking.ParkingModel;
import com.fabricio.parking.repository.model.parking.SlotModel;
import com.fabricio.parking.vo.order.OrderStatusEnum;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private OrderRepository orderRepository;
  private CustomerService customerService;
  private ParkingService parkingService;

  @Autowired
  public OrderService(OrderRepository orderRepository, CustomerService customerService, ParkingService parkingService) {
    this.orderRepository = orderRepository;
    this.customerService = customerService;
    this.parkingService = parkingService;
  }

  public OrderModel create(OrderModel orderModel) {
    processOrderCreation(orderModel);
    return orderRepository.save(orderModel);
  }

  public OrderModel update(OrderModel orderModel, String orderId) {
    processExistingOrder(orderModel, orderId);
    return orderRepository.save(orderModel);
  }

  public OrderModel closeOrder(final String orderId, final Long endDateTimeStamp) {

    OrderModel orderModel = findOrderById(orderId);
    orderModel.setEndDate(OffsetDateTimeHelper.fromTimestamp(endDateTimeStamp));
    orderModel.setStatus(OrderStatusEnum.CLOSED.toString());
    validateDates(orderModel);
    ParkingModel parkingModel = parkingService.findById(orderModel.getParkingId());
    orderModel.setPrice(OrderHelper.calculatePrice(orderModel, parkingModel));
    orderRepository.save(orderModel);
    return orderModel;

  }

  public OrderModel findOrderById(String orderId) {
    Optional<OrderModel> optionalOrderModel = orderRepository.findById(orderId);
    if (!optionalOrderModel.isPresent()) {
      throw NotFoundException.notFound(IssueEnum.ORDER_NOT_FOUND, orderId);
    }
    return optionalOrderModel.get();
  }


  private void processOrderCreation(OrderModel orderModel) {

    if (orderModel.getId() != null) {
      Optional<OrderModel> optionalOrderModel = orderRepository.findById(orderModel.getId());

      if (optionalOrderModel.isPresent()) {
        throw BadRequestException.businessRule(IssueEnum.ORDER_ALREADY_EXISTS, orderModel.getId());
      }
    }

    CustomerModel customerModel = customerService.findById(orderModel.getCustomerId());
    ParkingModel parkingModel = parkingService.findById(orderModel.getParkingId());
    Optional<CarModel> carModel = customerModel.getCars().stream().filter(carModel1 -> carModel1.getPlate().equals(orderModel.getPlateCar())).findFirst();

    if (!carModel.isPresent()) {
      throw NotFoundException.notFound(IssueEnum.PLATE_CAR_NOT_FOUND_FOR_CUSTOMER, orderModel.getPlateCar(), orderModel.getCustomerId());
    }

    Optional<SlotModel> slotModel = parkingModel.getSlots().stream().filter(slotModel1 -> slotModel1.getType().equals(carModel.get().getType())).findFirst();
    if (!slotModel.isPresent() || (slotModel.get().getNumberOfAvailableSlots() < 1)) {
      throw BadRequestException.businessRule(IssueEnum.NO_AVAILABLE_SLOT, slotModel.get().getType());
    }

    slotModel.get().setNumberOfAvailableSlots(slotModel.get().getNumberOfAvailableSlots() - 1);
    parkingService.save(parkingModel);

  }

  private void processExistingOrder(OrderModel orderModel, String orderId) {

    Optional<OrderModel> optionalOrderModel = orderRepository.findById(orderId);

    if (optionalOrderModel.get().getStatus().equals(OrderStatusEnum.CLOSED.toString())) {
      throw BadRequestException.businessRule(IssueEnum.CLOSED_ORDER_CANNOT_BE_UPDATED, orderModel.getId());
    }

    parkingService.findById(orderModel.getParkingId());
    CustomerModel customerModel = customerService.findById(orderModel.getCustomerId());
    Optional<CarModel> carModel = customerModel.getCars().stream().filter(carModel1 -> carModel1.getPlate().equals(orderModel.getPlateCar())).findFirst();

    if (!carModel.isPresent()) {
      throw NotFoundException.notFound(IssueEnum.PLATE_CAR_NOT_FOUND_FOR_CUSTOMER, orderModel.getPlateCar(), orderModel.getCustomerId());
    }

  }

  private void validateDates(OrderModel orderModel) {
    if (orderModel.getEntryDate() != null && orderModel.getEndDate() != null && !orderModel.getEndDate().isAfter(orderModel.getEntryDate())) {
      throw BadRequestException.businessRule(IssueEnum.INVALID_DATE_COMBINATION);
    }
  }


}