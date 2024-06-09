package org.example.Model.Rooms;

import org.example.Model.Resource;
import org.example.Model.RoomThread;

public class Bathroom extends RoomThread {

    public Bathroom(Resource resource, int consumptionRate) {
        super(resource, consumptionRate);
    }

    @Override
    public void run() {
        while (active) {
            try {
                Thread.sleep(1000); // Черпи ресурс всяка секунда
                if (resource.consume(consumptionRate)) {
                    //System.out.println("Bathroom consuming " + consumptionRate + " units of water.");
                } else {
                    System.out.println("Bathroom could not consume resource. Not enough water.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}