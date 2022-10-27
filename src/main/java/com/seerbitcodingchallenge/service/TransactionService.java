package com.seerbitcodingchallenge.service;

import com.seerbitcodingchallenge.dto.TransactionRequestDto;
import com.seerbitcodingchallenge.dto.TransactionResponseDto;
import com.seerbitcodingchallenge.entity.Statistics;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<TransactionResponseDto> postTransaction(TransactionRequestDto transaction);

    ResponseEntity<Statistics> getTransactionsStatistics();
}
