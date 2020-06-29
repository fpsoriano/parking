package com.fabricio.parking.vo.customer;

import static com.fabricio.parking.helper.ValidationConstants.*;

import com.fabricio.parking.repository.model.customer.CustomerModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVo {

  @ApiModelProperty(example = "4571", notes = "Customer ID")
  @NotNull(message = ID_IS_REQUIRED)
  private String id;

  @ApiModelProperty(example = "Fabricio Soriano Palhavam", notes = "Customer name")
  @NotNull(message = NAME_IS_REQUIRED)
  private String name;

  @ApiModelProperty(example = "19993387464", notes = "Customer cellphone")
  @NotNull(message = CELLPHONE_IS_REQUIRED)
  private String cellphone;

  @Valid
  private List<CarVo> cars;

  public CustomerModel toCustomerModel() {
    return CustomerModel.builder().id(this.id).name(this.name).cellphone(this.cellphone).cars(this.cars.stream().map(
        CarVo::toCarModel).collect(
        Collectors.toList())).build();
  }
}
