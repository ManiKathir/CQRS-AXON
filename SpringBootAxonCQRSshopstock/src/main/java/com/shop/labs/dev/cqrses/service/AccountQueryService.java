package com.shop.labs.dev.cqrses.service;

import com.shop.labs.dev.cqrses.entity.ItemStockAccount;
import com.shop.labs.dev.cqrses.query.FindStockAccountQuery;
import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.shop.labs.dev.cqrses.service.ServiceUtils.formatUuid;

@Service
@AllArgsConstructor
public class AccountQueryService {
    private final QueryGateway queryGateway;
    private final EventStore eventStore;

    public CompletableFuture<ItemStockAccount> findById(String itemId) {
        return this.queryGateway.query(
                new FindStockAccountQuery(formatUuid(itemId)),
                ResponseTypes.instanceOf(ItemStockAccount.class)
        );
    }

    public List<Object> listEventsForAccount(String itemId) {
        return this.eventStore
                .readEvents(formatUuid(itemId).toString())
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
