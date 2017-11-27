package com.ubb.ppp.beans;

/**
 * @author Marius Adam
 */
public class Order implements Comparable<Order>{
    private int id;
    private int price;
    private int quantity;

    public Order(int id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }


    @Override
    public int compareTo(Order o) {
        return id - o.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id == order.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}