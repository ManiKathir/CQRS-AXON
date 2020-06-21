package com.shop.labs.dev.cqrses.projection;

import com.shop.labs.dev.cqrses.entity.ItemStockAccount;
import com.shop.labs.dev.cqrses.event.StockItemCreatedEvent;
import com.shop.labs.dev.cqrses.event.StockCreditedEvent;
import com.shop.labs.dev.cqrses.event.StockDebitedEvent;
import com.shop.labs.dev.cqrses.exception.AccountNotFoundException;
import com.shop.labs.dev.cqrses.query.FindStockAccountQuery;
import com.shop.labs.dev.cqrses.repository.StockAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ShopStockAccountProjection {

    private final StockAccountRepository repository;
    private final QueryUpdateEmitter updateEmitter;


    @EventHandler
    public void on(StockItemCreatedEvent event) {
        log.debug("Handling a Stock Account creation command {}", event.getId());
        ItemStockAccount itemStockAccount = new ItemStockAccount(
                event.getId(),
                event.getItemName(),
                event.getInitialStock()
        );
        this.repository.save(itemStockAccount);
    }

    @EventHandler
    public void on(StockCreditedEvent event) throws AccountNotFoundException {
        log.debug("Handling a Stock Account Credit command {}", event.getId());
        Optional<ItemStockAccount> optionalStockAccount = this.repository.findById(event.getId());
        if (optionalStockAccount.isPresent()) {
            ItemStockAccount itemStockAccount = optionalStockAccount.get();
            itemStockAccount.setBalance(itemStockAccount.getBalance().add(event.getCreditAmount()));
            this.repository.save(itemStockAccount);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

    @EventHandler
    public void on(StockDebitedEvent event) throws AccountNotFoundException {
        log.debug("Handling a Stock Account Debit command {}", event.getId());
        Optional<ItemStockAccount> optionalStockAccount = this.repository.findById(event.getId());
        if (optionalStockAccount.isPresent()) {
            ItemStockAccount itemStockAccount = optionalStockAccount.get();
            itemStockAccount.setBalance(itemStockAccount.getBalance().subtract(event.getDebitAmount()));
            this.repository.save(itemStockAccount);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

    @QueryHandler
    public ItemStockAccount handle(FindStockAccountQuery query) {
        log.debug("Handling FindStockAccountQuery query: {}", query);
        return this.repository.findById(query.getItemId()).orElse(null);
    }
}
