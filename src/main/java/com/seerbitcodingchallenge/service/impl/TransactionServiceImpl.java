package com.seerbitcodingchallenge.service.impl;

import com.seerbitcodingchallenge.entity.Statistics;
import com.seerbitcodingchallenge.entity.Transaction;
import com.seerbitcodingchallenge.dto.TransactionRequestDto;
import com.seerbitcodingchallenge.dto.TransactionResponseDto;
import com.seerbitcodingchallenge.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Date;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Override
    public ResponseEntity<TransactionResponseDto> postTransaction(TransactionRequestDto transactionRequestDto) {
        Date date;
        Transaction transaction;

        // attempt to parse string to date and create transaction afterwards
        try {
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(transactionRequestDto.getTimestamp());
            Instant i = Instant.from(ta);
            date = Date.from(i);

            if (new Date().getTime() - date.getTime() > 30000L) { // 30000ms == 30secs

                // the NO_CONTENT code (204) is returned if date is older than 30 seconds
                return new ResponseEntity<>(new TransactionResponseDto(), HttpStatus.NO_CONTENT);

            } else if (date.after(new Date())) {

                // the UNPROCESSABLE_ENTITY code (422) is returned if date is in the future
                return new ResponseEntity<>(new TransactionResponseDto(), HttpStatus.UNPROCESSABLE_ENTITY);

            } else {

                // the transaction entity is created at this point if everything goes fine
                transaction = new Transaction(new BigDecimal(transactionRequestDto.getAmount()), date);

            }

        } catch (Exception e) {

            // the UNPROCESSABLE_ENTITY code (422) is returned if fields are not parsable
            return new ResponseEntity<>(new TransactionResponseDto(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // to be returned if things go fine (body is empty as required in the docs) with code 201 (created)
        return new ResponseEntity<>(new TransactionResponseDto(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Statistics> getTransactionsStatistics() {
        // assuming these are the transactions in the db
        Transaction transaction = new Transaction(BigDecimal.valueOf(500.018), new Date(new Date().getTime() - 90000L)); // 90000ms == 90s
        Transaction transaction1 = new Transaction(BigDecimal.valueOf(20500.526), new Date());
        Transaction transaction2 = new Transaction(BigDecimal.valueOf(400000.0123), new Date());
        Transaction transaction3 = new Transaction(BigDecimal.valueOf(80000.04), new Date(new Date().getTime() - 500L)); // 500ms == 0.5s

        // making a list of all transactions
        Transaction[] transactionsList = new Transaction[]{transaction, transaction1, transaction2, transaction3};

        // filtering transactions not more than 30 secs old
        // this includes transaction1, transaction2, transaction3
        Transaction[] transactionsNotMoreThan30SecOld = Arrays.stream(transactionsList)
                .filter(trans -> new Date().getTime() - trans.getTimestamp().getTime() < 30000L)
                .toArray(Transaction[]::new);

        // values for Statistics entity
        BigDecimal ave;
        long count = transactionsNotMoreThan30SecOld.length;
        BigDecimal max = BigDecimal.valueOf(Integer.MIN_VALUE);
        BigDecimal min = BigDecimal.valueOf(Integer.MAX_VALUE);
        BigDecimal sum = BigDecimal.valueOf(0);

        // determining the min and max and sum
        for (Transaction value : transactionsNotMoreThan30SecOld) {
            BigDecimal val = value.getAmount();

            // determining max and min amounts
            if (val.compareTo(max) > 0) {
                max = val;
            }

            if (val.compareTo(min) < 0) {
                min = val;
            }

            sum = sum.add(val);
        }

        // rounding up all values
        ave = sum.divide(BigDecimal.valueOf(count)).setScale(2, BigDecimal.ROUND_HALF_UP);
        max = max.setScale(2, BigDecimal.ROUND_HALF_UP);
        min = min.setScale(2, BigDecimal.ROUND_HALF_UP);
        sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);

        Statistics statistics = new Statistics(String.valueOf(sum), String.valueOf(ave), String.valueOf(max), String.valueOf(min), count);

        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
