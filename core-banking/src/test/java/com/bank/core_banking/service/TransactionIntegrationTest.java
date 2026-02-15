package com.bank.core_banking.service;

import com.bank.core_banking.model.Account;
import com.bank.core_banking.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TransactionIntegrationTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldPerformAtomicDatabaseTransfer() {
        // GIVEN
        accountRepository.save(new Account("SK_SENDER", new BigDecimal("1000.00")));
        accountRepository.save(new Account("SK_RECEIVER", new BigDecimal("500.00")));

        // WHEN
        transactionService.transferMoney("SK_SENDER", "SK_RECEIVER", new BigDecimal("200.00"));

        // THEN
        Account sender = accountRepository.findByIban("SK_SENDER").orElseThrow();
        Account receiver = accountRepository.findByIban("SK_RECEIVER").orElseThrow();

        assertThat(sender.getBalance()).isEqualByComparingTo("800.00");
        assertThat(receiver.getBalance()).isEqualByComparingTo("700.00");
    }
}
