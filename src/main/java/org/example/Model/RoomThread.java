package org.example.Model;

public abstract class RoomThread extends Thread {
    protected Resource resource;
    protected int consumptionRate;
    protected boolean active;
    protected boolean paused;

    public RoomThread(Resource resource, int consumptionRate) {
        this.resource = resource;
        this.consumptionRate = consumptionRate;
        this.active = true;
        this.paused = false;
    }

    public abstract void run();

    public synchronized void pauseConsumption() {
        this.paused = true;
    }

    public synchronized void resumeConsumption() {
        this.paused = false;
        notify();
    }

    public void stopRoom() {
        this.active = false;
    }

    protected synchronized void checkPaused() throws InterruptedException {
        while (paused) {
            wait();
        }
    }
}
