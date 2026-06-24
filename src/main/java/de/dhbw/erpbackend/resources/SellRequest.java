package de.dhbw.erpbackend.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class SellRequest {
    private int quantity;
    private BigDecimal sellUnitPrice;
    private Long customerId;
}
