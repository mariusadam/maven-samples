package com.tora;

import java.io.*;

/**
 * @author Marius Adam
 */
public class BigDecimalHelperSerializer {
    public BigDecimalHelper unserialize(String filename) {
        try (FileInputStream fileOut = new FileInputStream(filename);
             ObjectInputStream out = new ObjectInputStream(fileOut)) {

            return (BigDecimalHelper) out.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void serialize(BigDecimalHelper helper, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(helper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
