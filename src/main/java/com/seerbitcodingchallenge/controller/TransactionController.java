package com.seerbitcodingchallenge.controller;

import com.seerbitcodingchallenge.entity.Statistics;
import com.seerbitcodingchallenge.dto.TransactionRequestDto;
import com.seerbitcodingchallenge.dto.TransactionResponseDto;
import com.seerbitcodingchallenge.dto.TransactionDeletionDto;
import com.seerbitcodingchallenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    // handles "GET /statistics" end-point
    @GetMapping("/statistics")
    public ResponseEntity<Statistics> getStatistics() {
        return transactionService.getTransactionsStatistics();
    }

    // handles "POST /transactions" end-point
    @PostMapping(value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDto> postTransaction(@RequestBody TransactionRequestDto transaction) {
        return transactionService.postTransaction(transaction);
    }

    // handles "DELETE /transactions" end-point
    @DeleteMapping(value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteTransaction(@RequestBody TransactionDeletionDto transactionDeletionDto) {
        // this method accepts empty body and returns just the NO_CONTENT code
        return HttpStatus.NO_CONTENT;
    }
}
