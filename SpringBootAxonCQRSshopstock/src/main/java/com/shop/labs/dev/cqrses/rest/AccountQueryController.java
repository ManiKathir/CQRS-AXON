package com.shop.labs.dev.cqrses.rest;

import com.shop.labs.dev.cqrses.entity.ItemStockAccount;
import com.shop.labs.dev.cqrses.service.AccountQueryService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/accounts")
@Api(value = "Stock Account Queries", description = "Stock Account Query Events API")
@AllArgsConstructor
public class AccountQueryController {

    private final AccountQueryService accountQueryService;

    @GetMapping("/{itemId}")
    public CompletableFuture<ItemStockAccount> findById(@PathVariable("itemId") String itemId) {
        return this.accountQueryService.findById(itemId);
    }

    @GetMapping("/{itemId}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "itemId") String itemId) {
        return this.accountQueryService.listEventsForAccount(itemId);
    }
}
