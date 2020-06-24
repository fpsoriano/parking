package com.fabricio.parking.repository.model.order;

import com.fabricio.parking.vo.order.OrderVo;
import com.fabricio.parking.vo.order.OrderStatusEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class OrderModel {

  private String id;
  private String customerId;
  private String parkingId;
  private String plateCar;
  private OffsetDateTime entryDate;
  private OffsetDateTime endDate;
  private BigDecimal price;
  private String status;

  public OrderVo toOrderVo() {
    return OrderVo.builder()
        .id(this.id)
        .customerId(this.customerId)
        .parkingId(this.parkingId)
        .plateCar(this.plateCar)
        .entryDate(this.entryDate != null ? this.entryDate.toString() : null)
        .endDate(this.endDate != null ? this.endDate.toString() : null)
        .price(this.price)
        .status(this.status)
        .build();
  }

}
