package com.seerbitcodingchallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private String sum;
    private String ave;
    private String max;
    private String min;
    private long count;
}
