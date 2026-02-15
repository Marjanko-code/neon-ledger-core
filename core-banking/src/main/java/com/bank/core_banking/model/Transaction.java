package com.bank.core_banking.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderIban;
    private String receiverIban;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Transaction() {}

    // Getters and Setters
    public Long getId() { return id; }
    public String getSenderIban() { return senderIban; }
    public void setSenderIban(String senderIban) { this.senderIban = senderIban; }
    public String getReceiverIban() { return receiverIban; }
    public void setReceiverIban(String receiverIban) { this.receiverIban = receiverIban; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}