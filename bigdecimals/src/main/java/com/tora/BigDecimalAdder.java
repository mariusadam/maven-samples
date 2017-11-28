package com.tora;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class BigDecimalAdder implements Function<List<BigDecimal>, BigDecimal>, Serializable {
    @Override
    public BigDecimal apply(List<BigDecimal> decimals) {
        return decimals
                .stream()
                .parallel()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
