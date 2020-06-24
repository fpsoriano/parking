package com.fabricio.parking.repository.model.parking;

import com.fabricio.parking.vo.parking.ParkingVo;
import com.fabricio.parking.vo.parking.SlotVo;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class ParkingModel {

  @Id
  private String id;
  private String name;
  private String address;
  private PriceModel priceDetails;
  private List<SlotModel> slots;

  public ParkingVo toParkingVo() {
    return ParkingVo.builder()
        .id(this.id)
        .name(this.name)
        .address(this.address)
        .priceDetails(this.getPriceDetails().toPriceVo())
        .slots(this.slots.stream().map(SlotModel::toParkingModel).collect(Collectors.toList())).build();
  }

}
