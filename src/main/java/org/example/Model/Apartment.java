package org.example.Model;

import org.example.Model.Rooms.Bathroom;
import org.example.Model.Rooms.LivingRoom;

public class Apartment {
    private LivingRoom livingRoom;
    private Bathroom bathroom;

    public Apartment(Resource waterResource, Resource electricityResource, int consumptionRate) {
        this.livingRoom = new LivingRoom(electricityResource, consumptionRate);
        this.bathroom = new Bathroom(waterResource, consumptionRate);
    }

    public void startLivingRoom(){
        if(!livingRoom.isAlive()){
            try {
                livingRoom.start();
            } catch(IllegalThreadStateException ignored) {}
        }else{
            livingRoom.resumeConsumption();
            System.out.println("Living room is already running");
        }

    }
    public void startBathroom() {
        if(!bathroom.isAlive()){
            try {
                bathroom.start();
            } catch(IllegalThreadStateException ignored) {}
        }else{
            bathroom.resumeConsumption();
            System.out.println("Bathroom is already running");
        }

    }
    public void startRooms(){
        if(!bathroom.isAlive() && !livingRoom.isAlive()){
            livingRoom.start();
            bathroom.start();
        }else{ //proverqvame pootdelno ako nqkoq e runnata, togava ne mojem da pusnem 2te i trqbva da gi izkluchim purvo 2te i togava da gi pusnem
            if(bathroom.isAlive()){
                bathroom.resumeConsumption();
                System.out.println("Bathroom is already running");
            }else if(livingRoom.isAlive()){
                livingRoom.resumeConsumption();
                System.out.println("Bathroom is already running");
            }else{
                livingRoom.resumeConsumption();
                bathroom.resumeConsumption();
                System.out.println("Both room are already running");
            }
        }
    }

    public void stopLivingRoom(){
        livingRoom.stopRoom();
    }
    public void stopBathroom() {
        bathroom.stopRoom();
    }
    public void stopRooms(){
        livingRoom.stopRoom();
        bathroom.stopRoom();
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public LivingRoom getLivingRoom() {
        return livingRoom;
    }
}
