package com.fabricio.parking.helpers;

import com.fabricio.parking.vo.order.OrderVo;
import com.fabricio.parking.vo.parking.ParkingVo;
import com.fabricio.parking.vo.parking.PriceVo;
import com.fabricio.parking.vo.parking.PricingPolicyEnum;
import com.fabricio.parking.vo.parking.SlotVo;
import com.fabricio.parking.vo.parking.TypeEnum;
import java.math.BigDecimal;
import java.util.Arrays;

public class OrderHelper {

  public static OrderVo mockOrderVo() {
    OrderVo orderVo = new OrderVo();
    orderVo.setCustomerId("customerId");
    orderVo.setEntryDate("2020-06-23T19:30:00.999Z");
    orderVo.setEndDate("2020-06-23T23:00Z");
    orderVo.setPlateCar("TMM-3434");
    orderVo.setId("orderId");
    orderVo.setParkingId("parkingId");
    orderVo.setStatus("OPEN");
    return orderVo;
  }

}
