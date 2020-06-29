package com.fabricio.parking.vo.parking;

import static com.fabricio.parking.helper.ValidationConstants.PRICING_POLICY_IS_REQUIRED;

import com.fabricio.parking.repository.model.parking.PriceModel;
import com.fabricio.parking.vo.parking.validator.PricingPolicy;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceVo {


  @ApiModelProperty(example = "$2.00", notes = "Fixed Amount to calculate the price")
  private BigDecimal fixedAmount;

  @ApiModelProperty(example = "$1.00", notes = "Hour price to calculate the price")
  private BigDecimal hourPrice;

  @NotBlank(message = PRICING_POLICY_IS_REQUIRED)
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
