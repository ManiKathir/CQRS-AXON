package com.shop.labs.dev.cqrses.rest;

import com.shop.labs.dev.cqrses.entity.ItemStockAccount;
import com.shop.labs.dev.cqrses.rest.dto.AccountCreationDTO;
import com.shop.labs.dev.cqrses.rest.dto.MoneyAmountDTO;
import com.shop.labs.dev.cqrses.service.AccountCommandService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/accounts")
@Api(value = "Stock Account Commands", description = "Stock Account Commands API")
@AllArgsConstructor
public class AccountCommandController {
	
    private final AccountCommandService accountCommandService;

    @PostMapping
    @ResponseStatus(value = CREATED)
    public CompletableFuture<ItemStockAccount> createAccount(@RequestBody AccountCreationDTO creationDTO) {
        return this.accountCommandService.createAccount(creationDTO);
    }

    @PutMapping(value = "/credit/{itemId}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "itemId") String itemId,
                                                          @RequestBody MoneyAmountDTO moneyCreditDTO) {
        return this.accountCommandService.creditMoneyToAccount(itemId, moneyCreditDTO);
    }

    @PutMapping(value = "/debit/{itemId}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "itemId") String itemId,
                                                           @RequestBody MoneyAmountDTO moneyDebitDTO) {
        return this.accountCommandService.debitMoneyFromAccount(itemId, moneyDebitDTO);
    }
}
