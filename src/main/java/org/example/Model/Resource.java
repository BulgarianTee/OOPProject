package org.example.Model;

import org.example.Service.ThreadUtils;

import java.util.concurrent.Semaphore;

public class Resource {
    private int amount;
    private final Semaphore semaphore;

    public Resource(int initialAmount) {
        this.amount = initialAmount;
        this.semaphore = new Semaphore(ThreadUtils.maxThreads);
    }

    public boolean consume(int amount) {
        try {
            semaphore.acquire();
            synchronized (this) {
                if (this.amount >= amount) {
                    this.amount -= amount;
                    semaphore.release();
                    return true;
                }
                this.amount -= amount;
                semaphore.release();
                return false;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

    public void replenish(int amount) { //refill the resource
        try {
            semaphore.acquire();
            synchronized (this) {
                this.amount += amount;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

    public int getAmount() {

        int amountToReturn;

        try {
            semaphore.acquire();
            synchronized (this) {
                amountToReturn = amount;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
        return amountToReturn;
    }
}
