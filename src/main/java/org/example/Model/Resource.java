package org.example.Model;

public class Resource {
    private int amount;

    public Resource(int initialAmount) {
        this.amount = initialAmount;
    }

    public synchronized boolean consume(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            return true;
        }
        return false;
    }

    public synchronized void replenish(int amount) { //refill the resource
        this.amount += amount;
    }

    public synchronized int getAmount() {
        return amount;
    }
}
