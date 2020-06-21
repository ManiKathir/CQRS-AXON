package com.shop.labs.dev.cqrses.service;

import com.shop.labs.dev.cqrses.command.CreateItemStock;
import com.shop.labs.dev.cqrses.command.CreditMoneyCommand;
import com.shop.labs.dev.cqrses.command.DebitMoneyCommand;
import com.shop.labs.dev.cqrses.entity.ItemStockAccount;
import com.shop.labs.dev.cqrses.rest.dto.AccountCreationDTO;
import com.shop.labs.dev.cqrses.rest.dto.MoneyAmountDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.shop.labs.dev.cqrses.service.ServiceUtils.formatUuid;

@Service
@AllArgsConstructor
public class AccountCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<ItemStockAccount> createAccount(AccountCreationDTO creationDTO) {
        return this.commandGateway.send(new CreateItemStock(
                UUID.randomUUID(),
                creationDTO.getInitialBalance(),
                creationDTO.getOwner()
        ));
    }

    public CompletableFuture<String> creditMoneyToAccount(String itemId,
                                                          MoneyAmountDTO moneyCreditDTO) {
        return this.commandGateway.send(new CreditMoneyCommand(
                formatUuid(itemId),
                moneyCreditDTO.getAmount()
        ));
    }

    public CompletableFuture<String> debitMoneyFromAccount(String itemId,
                                                           MoneyAmountDTO moneyDebitDTO) {
        return this.commandGateway.send(new DebitMoneyCommand(
                formatUuid(itemId),
                moneyDebitDTO.getAmount()
        ));
    }
}
