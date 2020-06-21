package com.shop.labs.dev.cqrses.event;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class StockItemCreatedEvent {

    private final UUID id;
    private final BigDecimal initialStock;
    private final String itemName;
}
