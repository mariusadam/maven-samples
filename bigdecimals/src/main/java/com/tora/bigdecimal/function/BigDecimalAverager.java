package com.tora.bigdecimal.function;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class BigDecimalAverager implements Function<List<BigDecimal>, BigDecimal>, Serializable {
    @Override
    public BigDecimal apply(List<BigDecimal> decimals) {
        return decimals
                .stream()
                .parallel()
                .reduce(
                        new ImmutableAverager(),
                        ImmutableAverager::accept,
                        ImmutableAverager::combine
                )
                .average();
    }

    static class ImmutableAverager {
        private final BigDecimal total;
        private final int count;

        ImmutableAverager() {
            this.total = BigDecimal.ZERO;
            this.count = 0;
        }

        ImmutableAverager(BigDecimal total, int count) {
            this.total = total;
            this.count = count;
        }

        BigDecimal average() {
            return count > 0 ? total.divide(new BigDecimal(count), BigDecimal.ROUND_UP) : BigDecimal.ZERO;
        }

        ImmutableAverager accept(BigDecimal i) {
            return new ImmutableAverager(total.add(i), count + 1);
        }

        ImmutableAverager combine(ImmutableAverager other) {
            return new ImmutableAverager(total.add(other.total), count + other.count);
        }
    }
}
