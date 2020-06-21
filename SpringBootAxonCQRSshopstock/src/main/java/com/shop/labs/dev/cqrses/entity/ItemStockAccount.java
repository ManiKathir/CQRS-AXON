package com.shop.labs.dev.cqrses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ItemStockAccount {
    @Id
    private UUID id;
    private String itemName;
    private BigDecimal balance;
}
