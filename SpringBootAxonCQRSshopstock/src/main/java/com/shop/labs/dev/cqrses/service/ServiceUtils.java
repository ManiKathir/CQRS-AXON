package com.shop.labs.dev.cqrses.service;

import java.util.UUID;

public class ServiceUtils {

    public static UUID formatUuid(String itemId) {
        itemId = itemId.replace("-", "");
        String formatted = String.format(
                itemId.substring(0, 8) + "-" +
                        itemId.substring(8, 12) + "-" +
                        itemId.substring(12, 16) + "-" +
                        itemId.substring(16, 20) + "-" +
                        itemId.substring(20, 32)
        );
        return UUID.fromString(formatted);
    }
}
