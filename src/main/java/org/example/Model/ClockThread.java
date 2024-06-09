package org.example.Model;

public class ClockThread extends Thread {
    private long timeInSeconds;
    private boolean running;

    public ClockThread() {
        this.timeInSeconds = 0;
        this.running = true;
    }

    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                timeInSeconds++;
                //System.out.println("Time: " + timeInSeconds + " seconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopClock() {
        this.running = false;
    }

    public long getTimeInSeconds() {
        return timeInSeconds;
    }
}
