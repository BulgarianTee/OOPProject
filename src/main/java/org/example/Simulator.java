package org.example;

import org.example.Model.Apartment;
import org.example.Model.ClockThread;
import org.example.Model.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulator {
    private JFrame frame;
    private ClockThread clockThread;
    private Resource waterResource;
    private Resource electricityResource;
    private Apartment[] apartments;

    public Simulator(int numApartments, int initialResourceAmount, int consumptionRate) {
        this.clockThread = new ClockThread();
        this.waterResource = new Resource(initialResourceAmount);
        this.electricityResource = new Resource(initialResourceAmount);
        this.apartments = new Apartment[numApartments];

        for (int i = 0; i < numApartments; i++) {
            apartments[i] = new Apartment(waterResource, electricityResource, consumptionRate);
        }

        initializeGUI(numApartments);
    }

    private void initializeGUI(int numApartments) {
        frame = new JFrame("Apartment Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(numApartments, 4));

        for (int i = 0; i < numApartments; i++) {
            int apartmentIndex = i;
            
            JPanel livingRoomPanel = new JPanel();
            livingRoomPanel.setBackground(Color.GRAY);
            livingRoomPanel.setBorder(BorderFactory.createTitledBorder("Apartment " + (i + 1) + " - Living Room"));
            JButton startLivingRoomBtn = new JButton("Start Living Room");
            startLivingRoomBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    apartments[apartmentIndex].startLivingRoom();
                }
            });
            JButton stopLivingRoomBtn = new JButton("Stop Living Room");
            stopLivingRoomBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    apartments[apartmentIndex].stopLivingRoom();
                }
            });
            livingRoomPanel.add(startLivingRoomBtn);
            livingRoomPanel.add(stopLivingRoomBtn);
            frame.add(livingRoomPanel);

            JPanel bathroomPanel = new JPanel();
            bathroomPanel.setBackground(Color.GRAY);
            bathroomPanel.setBorder(BorderFactory.createTitledBorder("Apartment " + (i + 1) + " - Bathroom"));
            JButton startBathroomBtn = new JButton("Start Bathroom");
            startBathroomBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    apartments[apartmentIndex].startBathroom();
                }
            });
            JButton stopBathroomBtn = new JButton("Stop Bathroom");
            stopBathroomBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    apartments[apartmentIndex].stopBathroom();
                }
            });
            bathroomPanel.add(startBathroomBtn);
            bathroomPanel.add(stopBathroomBtn);
            frame.add(bathroomPanel);
        }

        frame.pack();
        frame.setVisible(true);
    }

    public void startSimulation() {
        clockThread.start();
        // Не стартираме стаите автоматично тук, тъй като ще се стартират чрез бутоните в GUI
    }

    public void stopSimulation() {
        clockThread.stopClock();
        for (Apartment apartment : apartments) {
            apartment.stopLivingRoom();
            apartment.stopBathroom();
        }
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator(10, 1000, 10);
        simulator.startSimulation();
    }
}
