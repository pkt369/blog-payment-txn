package dev.junlog.payment.txn.transaction.controller;

import dev.junlog.payment.txn.transaction.dao.Transaction;
import dev.junlog.payment.txn.transaction.dto.TransactionRequest;
import dev.junlog.payment.txn.transaction.dto.TransactionResponse;
import dev.junlog.payment.txn.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
        @RequestBody TransactionRequest request) {

        Transaction transaction = transactionService.createTransaction(request);
        TransactionResponse response = TransactionResponse.from(transaction);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        TransactionResponse response = TransactionResponse.from(transaction);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        List<TransactionResponse> responses = transactions.stream()
            .map(TransactionResponse::from)
            .toList();

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TransactionResponse> updateStatus(
        @PathVariable Long id,
        @RequestParam("status") String status) {

        Transaction updated = transactionService.updateStatus(id, status);
        TransactionResponse response = TransactionResponse.from(updated);

        return ResponseEntity.ok(response);
    }
}
