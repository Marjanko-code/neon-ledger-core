package com.bank.core_banking.service;

import com.bank.core_banking.exception.InsufficientFundsException;
import com.bank.core_banking.exception.ProtocolViolationException;
import com.bank.core_banking.model.Transaction;
import com.bank.core_banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Transaction Service - Core Logic Validation")
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction validTx;

    @BeforeEach
    void setUp() {
        validTx = new Transaction();
        validTx.setReceiverName("Geralt");
        validTx.setReceiverIban("SK99000011112222");
        validTx.setAmount(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("✅ SUCCESS: Balance should be deducted correctly")
    void processTransaction_Success() {
        // GIVEN
        when(transactionRepository.save(any(Transaction.class))).thenReturn(validTx);

        // WHEN
        transactionService.processTransaction(validTx);

        // THEN
        assertThat(transactionService.getBalance()).isEqualByComparingTo("4900.00");
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("❌ FAILURE: Should throw InsufficientFundsException")
    void processTransaction_InsufficientFunds() {
        // GIVEN
        validTx.setAmount(new BigDecimal("6000.00"));

        // WHEN & THEN
        assertThatThrownBy(() -> transactionService.processTransaction(validTx))
                .isInstanceOf(InsufficientFundsException.class);

        verify(transactionRepository, never()).save(any());
    }

    @Test
    @DisplayName("❌ FAILURE: Should throw ProtocolViolationException")
    void processTransaction_ProtocolViolation() {
        // GIVEN
        validTx.setReceiverIban("CZ123456789");

        // WHEN & THEN
        assertThatThrownBy(() -> transactionService.processTransaction(validTx))
                .isInstanceOf(ProtocolViolationException.class);

        verify(transactionRepository, never()).save(any());
    }
}
