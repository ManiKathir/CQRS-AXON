package com.shop.labs.dev.cqrses;

import com.shop.labs.dev.cqrses.aggregate.StockAccountAggregate;
import com.shop.labs.dev.cqrses.command.CreateItemStock;
import com.shop.labs.dev.cqrses.command.CreditMoneyCommand;
import com.shop.labs.dev.cqrses.command.DebitMoneyCommand;
import com.shop.labs.dev.cqrses.event.StockItemCreatedEvent;
import com.shop.labs.dev.cqrses.event.StockCreditedEvent;
import com.shop.labs.dev.cqrses.event.StockDebitedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class StockAccountTest {
    private static final String itemName = "sebrass";

    private FixtureConfiguration<StockAccountAggregate> fixture;
    private UUID id;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(StockAccountAggregate.class);
        id = UUID.randomUUID();
    }

    @Test
    public void should_dispatch_accountcreated_event_when_createaccount_command() {
        fixture.givenNoPriorActivity()
                .when(new CreateItemStock(
                        id,
                        BigDecimal.valueOf(1000),
                        itemName)
                )
                .expectEvents(new StockItemCreatedEvent(
                        id,
                        BigDecimal.valueOf(1000),
                        itemName)
                );
    }

    @Test
    public void should_dispatch_moneycredited_event_when_balance_is_lower_than_debit_amount() {
        fixture.given(new StockItemCreatedEvent(
                        id,
                        BigDecimal.valueOf(1000),
                        itemName))
                .when(new CreditMoneyCommand(
                        id,
                        BigDecimal.valueOf(100))
                )
                .expectEvents(new StockCreditedEvent(
                        id,
                        BigDecimal.valueOf(100))
                );
    }

    @Test
    public void should_dispatch_moneydebited_event_when_balance_is_upper_than_debit_amount() {
        fixture.given(new StockItemCreatedEvent(
                        id,
                        BigDecimal.valueOf(1000),
                        itemName))
                .when(new DebitMoneyCommand(
                        id,
                        BigDecimal.valueOf(100)))
                .expectEvents(new StockDebitedEvent(
                        id,
                        BigDecimal.valueOf(100)));
    }

    @Test
    public void should_not_dispatch_event_when_balance_is_lower_than_debit_amount() {
        fixture.given(new StockItemCreatedEvent(
                        id,
                        BigDecimal.valueOf(1000),
                        itemName))
                .when(new DebitMoneyCommand(
                        id,
                        BigDecimal.valueOf(5000)))
                .expectNoEvents();
    }
}
