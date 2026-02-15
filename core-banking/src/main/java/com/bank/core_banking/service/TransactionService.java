package com.bank.core_banking.service;

import com.bank.core_banking.exception.InsufficientFundsException;
import com.bank.core_banking.exception.ProtocolViolationException;
import com.bank.core_banking.model.Account;
import com.bank.core_banking.model.Transaction;
import com.bank.core_banking.repository.AccountRepository;
import com.bank.core_banking.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transferMoney(String fromIban, String toIban, BigDecimal amount) {
        log.info("Processing transfer from {} to {} | Amount: {}", fromIban, toIban, amount);

        if (!toIban.startsWith("SK")) {
            throw new ProtocolViolationException("Destination IBAN must be Slovak (SK)");
        }

        Account sender = accountRepository.findByIban(fromIban)
                .orElseThrow(() -> new RuntimeException("Sender account missing"));
        Account receiver = accountRepository.findByIban(toIban)
                .orElseThrow(() -> new RuntimeException("Receiver account missing"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds. Required: " + amount);
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction logEntry = new Transaction();
        logEntry.setSenderIban(fromIban);
        logEntry.setReceiverIban(toIban);
        logEntry.setAmount(amount);
        logEntry.setStatus("SUCCESS");
        transactionRepository.save(logEntry);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public BigDecimal getAccountBalance(String iban) {
        return accountRepository.findByIban(iban)
                .map(Account::getBalance)
                .orElseThrow(() -> new RuntimeException("Account missing"));
    }
}