package com.tora.bigdecimal.serializer;

import com.tora.bigdecimal.function.BigDecimalFunctions;

import java.io.*;

/**
 * @author Marius Adam
 */
public class BigDecimalFunctionsSerializer {
    public BigDecimalFunctions unserialize(String filename) {
        try (FileInputStream fileOut = new FileInputStream(filename);
             ObjectInputStream out = new ObjectInputStream(fileOut)) {

            return (BigDecimalFunctions) out.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void serialize(BigDecimalFunctions helper, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(helper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
