package com.shop.labs.dev.cqrses.repository;

import com.shop.labs.dev.cqrses.entity.ItemStockAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockAccountRepository extends JpaRepository<ItemStockAccount, UUID> {
}
