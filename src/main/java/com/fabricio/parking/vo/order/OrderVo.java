package com.fabricio.parking.vo.order;

import static com.fabricio.parking.helper.ValidationConstants.CUSTOMER_ID_IS_REQUIRED;
import static com.fabricio.parking.helper.ValidationConstants.PARKING_ID_IS_REQUIRED;
import static com.fabricio.parking.helper.ValidationConstants.PLATE_CAR_IS_REQUIRED;

import com.fabricio.parking.helper.OffsetDateTimeHelper;
import com.fabricio.parking.repository.model.order.OrderModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {

  @ApiModelProperty(example = "4571", notes = "Order ID.")
  private String id;

  @ApiModelProperty(example = "123F", notes = "Parking ID")
  @NotNull(message = PARKING_ID_IS_REQUIRED)
  private String parkingId;

  @ApiModelProperty(example = "4571", notes = "Customer ID")
  @NotNull(message = CUSTOMER_ID_IS_REQUIRED)
  private String customerId;

  @ApiModelProperty(example = "GMM-4755", notes = "Car plate")
  @NotNull(message = PLATE_CAR_IS_REQUIRED)
  private String plateCar;

  @ApiModelProperty(example = "2019-03-31T21:00:00.999Z", notes = "Entry date. If not provided, the application will get the the current time.")
  private String entryDate;

  @ApiModelProperty(example = "2019-03-31T23:20:00.999Z", notes = "Date-time that the car left the parking.")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String endDate;

  @ApiModelProperty(notes = "Price calculated by the application")
  private BigDecimal price;

  @ApiModelProperty(example = "OPEN", notes = "It always create with the value OPEN. To close an order you should call another API.")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String status;

  public OrderModel toOrderModel() {
    return OrderModel.builder()
        .id(this.id)
        .customerId(this.customerId)
        .parkingId(this.parkingId)
        .plateCar(this.plateCar)
        .entryDate(Optional.ofNullable(this.entryDate).map(OffsetDateTime::parse).orElse(
            OffsetDateTimeHelper.fromTimestamp(null)))
        .endDate(Optional.ofNullable(this.endDate).map(OffsetDateTime::parse).orElse(null))
        .status(OrderStatusEnum.OPEN.toString())
        .build();
  }

}
