package com.wefox.techtest.domain.service;

import com.wefox.techtest.store.AccountRepository;
import com.wefox.techtest.store.entity.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account findById(final String accountId) throws NoSuchElementException {
        log.debug("Finding account by id {} ", accountId);
        return accountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new NoSuchElementException("No Account found for id ".concat(accountId))
                );
    }
}
