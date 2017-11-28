package com.tora;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author Marius Adam
 */
public class BigDecimalTopNPercentReporter implements BiFunction<List<BigDecimal>, Double, List<BigDecimal>>, Serializable {
    @Override
    public List<BigDecimal> apply(List<BigDecimal> decimals, Double percent) {
        long safeLimit = (long) Math.ceil(decimals.size() * percent / 100);
        return decimals
                .stream()
                .parallel()
                .sorted((o1, o2) -> -1 * o1.compareTo(o2))
                .limit(safeLimit)
                .collect(Collectors.toList());
    }
}
