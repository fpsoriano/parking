package com.fabricio.parking.exceptions;

import java.util.IllegalFormatException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum IssueEnum {
  MALFORMED_REQUEST(1, "Malformed Request"),
  NO_AVAILABLE_SLOT(2, "No available slot for %s cars."),
  CUSTOMER_NOT_FOUND(3, "Customer %s not found."),
  PARKING_NOT_FOUND(4, "Parking %s not found."),
  PLATE_CAR_NOT_FOUND_FOR_CUSTOMER(5, "Car %s not found for the customer %s."),
  CLOSED_ORDER_CANNOT_BE_UPDATED(6, "The order %s cannot be updated because is already closed"),
  INVALID_DATE_COMBINATION(7, "The endDate date must be later than the entry date."),
  ORDER_NOT_FOUND(8, "Order %s not found."),
  ORDER_ALREADY_EXISTS(9, "Order %s already exists.");


  private final int code;
  private final String message;

  IssueEnum(final int code, final String message) {

    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getFormattedMessage(final Object... args) {

    if (message == null) {
      return "";
    }

    try {
      return String.format(message, args);
    } catch (final IllegalFormatException e) {
      log.error(e.getMessage(), e);
      return message.replace("%s", "");
    }
  }

}
