package com.tora.bigdecimal.pc;

import java.math.BigDecimal;

/**
 * @author Marius Adam
 */
public class Message {
    private String objectId;
    private BigDecimal payload;

    public Message(String objectId, BigDecimal payload) {
        this.objectId = objectId;
        this.payload = payload;
    }

    public String getObjectId() {
        return objectId;
    }

    public BigDecimal getPayload() {
        return payload;
    }
}
