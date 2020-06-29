package com.fabricio.parking.repository.model.parking;

import com.fabricio.parking.vo.parking.PriceVo;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceModel {

  private BigDecimal fixedAmount;
  private BigDecimal hourPrice;
  private String pricingPoliciy;

  public PriceVo toPriceVo() {
    return PriceVo.builder()
        .fixedAmount(this.fixedAmount)
        .hourPrice(this.hourPrice)
        .pricingPoliciy(this.pricingPoliciy)
        .build();
  }

}
