package com.shop.labs.dev.cqrses.aggregate;

import com.shop.labs.dev.cqrses.command.CreateItemStock;
import com.shop.labs.dev.cqrses.command.CreditMoneyCommand;
import com.shop.labs.dev.cqrses.command.DebitMoneyCommand;
import com.shop.labs.dev.cqrses.event.StockItemCreatedEvent;
import com.shop.labs.dev.cqrses.event.StockCreditedEvent;
import com.shop.labs.dev.cqrses.event.StockDebitedEvent;
import com.shop.labs.dev.cqrses.exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
public class StockAccountAggregate {

    @AggregateIdentifier
    private UUID id;
    private BigDecimal balance;
    private String owner;

    @CommandHandler
    public StockAccountAggregate(CreateItemStock command) {

        AggregateLifecycle.apply(
                new StockItemCreatedEvent(
                        command.getItemId(),
                        command.getInitialStock(),
                        command.getItemName()
                )
        );
    }

    @EventSourcingHandler
    public void on(StockItemCreatedEvent event) {
        this.id = event.getId();
        this.owner = event.getItemName();
        this.balance = event.getInitialStock();
    }

    @CommandHandler
    public void handle(CreditMoneyCommand command) {
        AggregateLifecycle.apply(
                new StockCreditedEvent(
                        command.getItemId(),
                        command.getCreditAmount()
                )
        );
    }

    @EventSourcingHandler
    public void on(StockCreditedEvent event) {
        this.balance = this.balance.add(event.getCreditAmount());
    }

    @CommandHandler
    public void handle(DebitMoneyCommand command) {
        AggregateLifecycle.apply(
                new StockDebitedEvent(
                        command.getItemId(),
                        command.getDebitAmount()
                )
        );
    }

    @EventSourcingHandler
    public void on(StockDebitedEvent event) throws InsufficientBalanceException {
        if (this.balance.compareTo(event.getDebitAmount()) < 0) {
            throw new InsufficientBalanceException(event.getId(), event.getDebitAmount());
        }
        this.balance = this.balance.subtract(event.getDebitAmount());
    }
}
