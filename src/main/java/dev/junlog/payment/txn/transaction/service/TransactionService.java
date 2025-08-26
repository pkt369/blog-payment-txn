package dev.junlog.payment.txn.transaction.service;

import dev.junlog.payment.txn.transaction.dao.Transaction;
import dev.junlog.payment.txn.transaction.dao.TransactionStatus;
import dev.junlog.payment.txn.transaction.dto.TransactionRequest;
import dev.junlog.payment.txn.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction createTransaction(TransactionRequest request) {
        Transaction tx = Transaction.builder()
            .userId(request.getUserId())
            .amount(request.getAmount())
            .type(request.getType())
            .status(TransactionStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        // Simulate payment processing delay for testing TPS and transaction handling
        try {
            Thread.sleep(1000); // 1s delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean success = Math.random() > 0.01; // 99%
        tx.setStatus(success ? TransactionStatus.SUCCESS : TransactionStatus.FAILED);

        return transactionRepository.save(tx);
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional
    public Transaction updateStatus(Long id, String status) {
        TransactionStatus newStatus = TransactionStatus.valueOf(status.toUpperCase());
        return transactionRepository.updateStatus(id, newStatus);
    }
}
