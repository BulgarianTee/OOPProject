package org.example.Service;

import java.util.Random;

import org.example.Model.Apartment;
import org.example.Model.ClockThread;
import org.example.Model.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    private JFrame frame;
    private ClockThread clockThread;
    private Resource waterResource;
    private Resource electricityResource;
    private int happiness;
    private Apartment[] apartments;

    public Game(JFrame frame, int numApartments, int initialResourceAmount, int consumptionRate) {
        this.clockThread = new ClockThread();
        clockThread.start();
        this.frame = frame;
        this.waterResource = new Resource(initialResourceAmount);
        this.electricityResource = new Resource(initialResourceAmount);
        this.happiness = 100;

        this.apartments = new Apartment[numApartments];

        for (int i = 0; i < numApartments; i++) {
            apartments[i] = new Apartment(waterResource, electricityResource, consumptionRate);
            //apartments[i].getLivingRoom().setActive(false);
            //apartments[i].getBathroom().setActive(false);
        }

        initializeGUI(numApartments);
    }

    private void initializeGUI(int numApartments) {

        frame.setLayout(new GridLayout(0, 1));

        Game game = this;

        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.gray);
        statsPanel.setBorder(BorderFactory.createTitledBorder("Apartments Block Statistics Center"));
        JButton statsButton = new JButton("Display Statistics");
        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Current block statistics:");
                System.out.printf("Electricity remaining: %d\n", game.getElectricityResource().getAmount());
                System.out.printf("Water remaining: %d\n", game.getWaterResource().getAmount());
                System.out.printf("Tenants happiness: %d\n", game.happiness);
                System.out.printf("Time as manager: %d\n\n", game.getClockThread().getTimeInSeconds());
            }
        });
        statsPanel.add(statsButton);
        frame.add(statsPanel);

        for(int i = 0; i < numApartments; i++) {
            JPanel apartmentPanel = new JPanel();
            apartmentPanel.setBackground(Color.gray);
            apartmentPanel.setBorder(BorderFactory.createTitledBorder("Apartment " + (i + 1)));

            int apartmentIndex = i;

            JButton startLivingRoomBtn = new JButton("Start Living Room");
            startLivingRoomBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    apartments[apartmentIndex].startLivingRoom();
                    apartments[apartmentIndex].getLivingRoom().setGetsResource(true);
                    System.out.println("Started living room " + (apartmentIndex + 1));
                }
            });
            JButton stopLivingRoomBtn = new JButton("Stop Living Room");
            stopLivingRoomBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    apartments[apartmentIndex].stopLivingRoom();
                    apartments[apartmentIndex].getLivingRoom().setGetsResource(false);
                    System.out.println("Stopped living room " + (apartmentIndex + 1));
                }
            });
            apartmentPanel.add(startLivingRoomBtn);
            apartmentPanel.add(stopLivingRoomBtn);

            JButton startBathroomBtn = new JButton("Start Bathroom");
            startBathroomBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    apartments[apartmentIndex].startBathroom();
                    apartments[apartmentIndex].getBathroom().setGetsResource(true);
                    System.out.println("Started bath room " + (apartmentIndex + 1));
                }
            });
            JButton stopBathroomBtn = new JButton("Stop Bathroom");
            stopBathroomBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    apartments[apartmentIndex].stopBathroom();
                    apartments[apartmentIndex].getBathroom().setGetsResource(false);
                    System.out.println("Stopped bath room " + (apartmentIndex + 1));
                }
            });
            apartmentPanel.add(startBathroomBtn);
            apartmentPanel.add(stopBathroomBtn);

            frame.add(apartmentPanel);
        }

        frame.pack();

        frame.setVisible(true);

        this.beginGameLoop();
    }

    public void beginGameLoop() {

        Random random = new Random();

        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            decreaseHappiness();

            checkFail();

            if(0 == random.nextInt(5)) { // 1 in 5 chance
                int apartId = random.nextInt(apartments.length);
                Apartment apartment = this.apartments[apartId]; // select random apartment

                if(random.nextBoolean()) {
                    if(apartment.getBathroom().getWantsResource()) {
                        System.out.println("No more water for Bathroom " + (apartId+1));
                        apartment.getBathroom().setWantsResource(false);
                    }
                    else {
                        System.out.println("Water needed for Bathroom " + (apartId+1));
                        apartment.getBathroom().setWantsResource(true);
                    }
                }
                else {
                    if(apartment.getLivingRoom().getWantsResource()) {
                        System.out.println("No more electricity for Living room " + (apartId+1));
                        apartment.getLivingRoom().setWantsResource(false);
                    }
                    else {
                        System.out.println("Electricity needed for Living room " + (apartId+1));
                        apartment.getLivingRoom().setWantsResource(true);
                    }
                }
            }
        }
    }

    public void decreaseHappiness() {
        for(Apartment apartment : apartments) {
            if(apartment.getBathroom().getWantsResource() && (!apartment.getBathroom().getGetsResource())) {
                this.happiness--;
            }
            if(apartment.getLivingRoom().getWantsResource() && (!apartment.getLivingRoom().getGetsResource())) {
                this.happiness--;
            }
        }
    }

    public void checkFail() {
        if(this.happiness < 0
                || this.electricityResource.getAmount() < 0
                || this.waterResource.getAmount() < 0
                ) {
            System.out.println("GAME OVER!");
            System.out.println("Game statistics:");
            System.out.printf("Electricity remaining: %d\n", this.getElectricityResource().getAmount());
            System.out.printf("Water remaining: %d\n", this.getWaterResource().getAmount());
            System.out.printf("Tenants happiness: %d\n", this.happiness);
            System.out.printf("Time as manager: %d\n\n", this.getClockThread().getTimeInSeconds());

            System.exit(0);
        }
    }

    public ClockThread getClockThread() {
        return clockThread;
    }

    public Resource getElectricityResource() {
        return electricityResource;
    }

    public Resource getWaterResource() {
        return waterResource;
    }
}