package com.tora;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class BigDecimalHelper implements Serializable {
    private final Function<List<BigDecimal>, BigDecimal> adder;
    private final Function<List<BigDecimal>, BigDecimal> averager;
    private final BiFunction<List<BigDecimal>, Double, List<BigDecimal>> reporter;

    public BigDecimalHelper(Function<List<BigDecimal>, BigDecimal> adder, Function<List<BigDecimal>, BigDecimal> averager, BiFunction<List<BigDecimal>, Double, List<BigDecimal>> reporter) {
        this.adder = adder;
        this.averager = averager;
        this.reporter = reporter;
    }

    public static BigDecimalHelper create() {
        return new BigDecimalHelper(
                new BigDecimalAdder(),
                new BigDecimalAverager(),
                new BigDecimalTopNPercentReporter()
        );
    }

    public Function<List<BigDecimal>, BigDecimal> getAdder() {
        return adder;
    }

    public Function<List<BigDecimal>, BigDecimal> getAverager() {
        return averager;
    }

    public BiFunction<List<BigDecimal>, Double, List<BigDecimal>> getReporter() {
        return reporter;
    }
}
