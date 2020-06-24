package com.fabricio.parking.repository.model.parking;

import com.fabricio.parking.vo.parking.SlotVo;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class SlotModel {

  @Id
  private String type;
  private Integer numberOfSlots;
  private Integer numberOfAvailableSlots;

  public SlotVo toParkingModel(){
    return SlotVo.builder().type(this.type.toUpperCase()).numberOfSlots(this.numberOfSlots).numberOfAvailableSlots(this.numberOfAvailableSlots).build();
  }

}
