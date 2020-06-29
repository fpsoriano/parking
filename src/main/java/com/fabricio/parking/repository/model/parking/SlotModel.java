package com.fabricio.parking.repository.model.parking;

import com.fabricio.parking.vo.parking.SlotVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotModel {

  @Id
  private String type;
  private Integer numberOfSlots;
  private Integer numberOfAvailableSlots;

  public SlotVo toParkingModel(){
    return SlotVo.builder().type(this.type.toUpperCase()).numberOfSlots(this.numberOfSlots).numberOfAvailableSlots(this.numberOfAvailableSlots).build();
  }

}
