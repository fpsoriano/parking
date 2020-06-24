package com.fabricio.parking.repository.model.parking;

import com.fabricio.parking.vo.parking.PriceVo;
import com.fabricio.parking.vo.parking.validator.PricingPolicy;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
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
