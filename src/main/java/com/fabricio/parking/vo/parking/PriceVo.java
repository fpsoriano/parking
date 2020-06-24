package com.fabricio.parking.vo.parking;

import com.fabricio.parking.repository.model.parking.PriceModel;
import com.fabricio.parking.vo.parking.validator.PricingPolicy;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceVo {

  @ApiModelProperty(example = "$2.00", notes = "Fixed Amount to calculate the price")
  private BigDecimal fixedAmount;

  @ApiModelProperty(example = "$1.00", notes = "Hour price to calculate the price")
  private BigDecimal hourPrice;

  @NotBlank(message = "Pricing Policy is required")
  @PricingPolicy(message = "Pricing Policy not valid")
  @ApiModelProperty(example = "NBHOURS_HOURPRICE", notes = "Valid values:\n NBHOURS_HOURPRICE (nb hours * hour price) \nPOWER_SUPPLY_20W (fixed amount + nb hours * hour price)")
  private String pricingPoliciy;

  public PriceModel toPriceModel() {
    return PriceModel.builder()
        .fixedAmount(this.fixedAmount != null ? this.fixedAmount : BigDecimal.ZERO)
        .hourPrice(this.hourPrice != null ? this.hourPrice : BigDecimal.ZERO)
        .pricingPoliciy(this.pricingPoliciy)
        .build();
  }

}
