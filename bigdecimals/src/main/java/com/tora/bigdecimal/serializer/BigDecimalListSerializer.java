package com.tora.bigdecimal.serializer;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public interface BigDecimalListSerializer {
    public void serialize(List<BigDecimal> decimals);
    public List<BigDecimal> unserialize(int size);
}
