package com.fabricio.parking.helpers;

import com.fabricio.parking.repository.model.parking.ParkingModel;
import com.fabricio.parking.repository.model.parking.PriceModel;
import com.fabricio.parking.repository.model.parking.SlotModel;
import com.fabricio.parking.vo.parking.ParkingVo;
import com.fabricio.parking.vo.parking.PriceVo;
import com.fabricio.parking.vo.parking.PricingPolicyEnum;
import com.fabricio.parking.vo.parking.SlotVo;
import com.fabricio.parking.vo.parking.TypeEnum;
import java.math.BigDecimal;
import java.util.Arrays;

public class ParkingHelper {

  public static ParkingVo mockParkingVo() {
    ParkingVo parkingVo = new ParkingVo();
    parkingVo.setAddress("Address Test");
    parkingVo.setName("Name Test");
    parkingVo.setId("parkingId");
    parkingVo.setPriceDetails(mockPriceVo());
    parkingVo.setSlots(Arrays.asList(mockSlot()));
    return parkingVo;
  }

  public static SlotVo mockSlot() {
    SlotVo slotVo = new SlotVo();
    slotVo.setNumberOfAvailableSlots(2);
    slotVo.setNumberOfSlots(2);
    slotVo.setType(TypeEnum.SEDAN_CARS.toString());
    return slotVo;
  }

  public static PriceVo mockPriceVo() {
    PriceVo priceVo = new PriceVo();
    priceVo.setFixedAmount(BigDecimal.ZERO);
    priceVo.setHourPrice(BigDecimal.ONE);
    priceVo.setPricingPoliciy(PricingPolicyEnum.NBHOURS_HOURPRICE.toString());
    return priceVo;
  }

}
