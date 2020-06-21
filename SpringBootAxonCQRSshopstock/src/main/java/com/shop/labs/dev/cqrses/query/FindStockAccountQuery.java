package com.shop.labs.dev.cqrses.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindStockAccountQuery {
    private UUID itemId;
}
