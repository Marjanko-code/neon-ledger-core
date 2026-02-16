package com.bank.core_banking.service;

import com.bank.core_banking.exception.InsufficientFundsException;
import com.bank.core_banking.exception.ProtocolViolationException;
import com.bank.core_banking.model.Account;
import com.bank.core_banking.repository.AccountRepository;
import com.bank.core_banking.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Transaction Service - Business Logic Validation")
class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("❌ FAILURE: Should throw ProtocolViolationException for non-SK IBAN")
    void transfer_ProtocolViolation() {
        assertThatThrownBy(() ->
                transactionService.transferMoney("SK123", "CZ123", new BigDecimal("100.00")))
                .isInstanceOf(ProtocolViolationException.class);

        verify(transactionRepository, never()).save(any());
    }

    @Test
    @DisplayName("❌ FAILURE: Should throw InsufficientFundsException")
    void transfer_InsufficientFunds() {
        // GIVEN
        Account sender = new Account("SK_SENDER", new BigDecimal("50.00"));
        Account receiver = new Account("SK_RECEIVER", new BigDecimal("100.00"));

        when(accountRepository.findByIban("SK_SENDER")).thenReturn(Optional.of(sender));
        when(accountRepository.findByIban("SK_RECEIVER")).thenReturn(Optional.of(receiver));

        // WHEN & THEN
        assertThatThrownBy(() ->
                transactionService.transferMoney("SK_SENDER", "SK_RECEIVER", new BigDecimal("100.00")))
                .isInstanceOf(InsufficientFundsException.class);
    }
}
