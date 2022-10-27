package com.seerbitcodingchallenge.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.seerbitcodingchallenge.dto.TransactionRequestDto;
import com.seerbitcodingchallenge.dto.TransactionResponseDto;
import com.seerbitcodingchallenge.entity.Statistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TransactionServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TransactionServiceImplTest {
    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Test
    void testPostTransaction() {
        ResponseEntity<TransactionResponseDto> actualPostTransactionResult = transactionServiceImpl
                .postTransaction(new TransactionRequestDto("10", "Timestamp"));
        assertTrue(actualPostTransactionResult.hasBody());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, actualPostTransactionResult.getStatusCode());
        assertTrue(actualPostTransactionResult.getHeaders().isEmpty());
    }

    @Test
    void testGetTransactionsStatistics() {
        ResponseEntity<Statistics> actualTransactionsStatistics = transactionServiceImpl.getTransactionsStatistics();
        assertTrue(actualTransactionsStatistics.hasBody());
        assertTrue(actualTransactionsStatistics.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualTransactionsStatistics.getStatusCode());
        Statistics body = actualTransactionsStatistics.getBody();
        assertEquals(3L, body.getCount());
        assertEquals("166833.53", body.getAve());
        assertEquals("500500.58", body.getSum());
        assertEquals("400000.01", body.getMax());
        assertEquals("20500.53", body.getMin());
    }
}

