package com.fabricio.parking.helper;

import com.fabricio.parking.repository.model.order.OrderModel;
import com.fabricio.parking.repository.model.parking.ParkingModel;
import com.fabricio.parking.vo.parking.PricingPolicyEnum;
import java.math.BigDecimal;
import java.time.Duration;

public class OrderHelper {

  //need to implement
  public static BigDecimal calculatePrice(OrderModel orderModel, ParkingModel parkingModel) {

    BigDecimal price = BigDecimal.ZERO;
    Duration duration = Duration.between(orderModel.getEntryDate(), orderModel.getEndDate());
    BigDecimal nb_hours = new BigDecimal(duration.toMinutes() / 60);

    if (parkingModel.getPriceDetails().getPricingPoliciy().equals(PricingPolicyEnum.NBHOURS_HOURPRICE.toString())) {
      price = parkingModel.getPriceDetails().getHourPrice().multiply(nb_hours);
    }

    if (parkingModel.getPriceDetails().getPricingPoliciy().equals(PricingPolicyEnum.FIXEDAMOUNT_NBHOURS_HOURPRICE.toString())) {
      price = parkingModel.getPriceDetails().getHourPrice().multiply(nb_hours).add(parkingModel.getPriceDetails().getFixedAmount());
    }
    return price;
  }

}