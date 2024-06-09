package org.example.Model;

public abstract class RoomThread extends Thread {
    protected Resource resource;
    protected int consumptionRate;
    protected boolean active;
    protected boolean paused;
    protected boolean wantsResource;

    public RoomThread(Resource resource, int consumptionRate) {
        this.resource = resource;
        this.consumptionRate = consumptionRate;
        this.active = true;
        this.paused = false;
        this.wantsResource = false;
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

    public boolean getWantsResource() {
        return this.wantsResource;
    }

    public void setWantsResource(boolean bool) {
        this.wantsResource = bool;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean bool) {
        this.active = bool;
    }
}
