package org.example.Model.Rooms;

import org.example.Model.Resource;
import org.example.Model.RoomThread;

public class LivingRoom extends RoomThread {

    public LivingRoom(Resource resource, int consumptionRate) {
        super(resource, consumptionRate);
    }

    @Override
    public void run() {
        while (active) {
            try {
                checkPaused();
                Thread.sleep(1000); // get resource every second
                if (resource.consume(consumptionRate)) {
                    System.out.println("Living Room consuming " + consumptionRate + " units of electricity.");
                } else {
                    System.out.println("Living Room could not consume resource. Not enough electricity.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
