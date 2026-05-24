package de.dhbw.erpbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "sold_to_customer_id")
    private Customer soldTo;

    @Column(precision = 19, scale = 4)
    private BigDecimal sellUnitPrice;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal unitBuyPrice;

    @ManyToOne
    @JoinColumn(name = "parent_item_id")
    private Item parent;
}
