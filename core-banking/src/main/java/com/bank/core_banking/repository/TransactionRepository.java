package com.bank.core_banking.repository;

import com.bank.core_banking.model.Transaction; // Importujeme našu entitu
import org.springframework.data.jpa.repository.JpaRepository; // Importujeme nástroj od Springu
import org.springframework.stereotype.Repository; // Označenie, že ide o úložisko dát

@Repository // kuriér pre databázu"
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
