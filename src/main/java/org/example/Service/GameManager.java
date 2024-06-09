package org.example.Service;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class GameManager {

    public GameManager() {

        JFrame frame;
        Menu menu;
        Game game;

        frame = new JFrame("Apartment Simulator");
        frame.setVisible(true);
        frame.setSize(WindowUtils.windowWidth, WindowUtils.windowHeight);
        frame.setPreferredSize(new Dimension(WindowUtils.windowWidth, WindowUtils.windowHeight));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menu = new Menu();
        frame.add(menu);
        frame.pack();

        Scanner scanner = new Scanner(System.in);
        int userInput = 0;
        while(userInput < 1 || userInput > 16) {
            try {
                userInput = Integer.parseInt(scanner.nextLine());
            } catch(Exception ignored) {

            }
        }

        game = new Game(frame, userInput, 200, 1);

        System.out.println(userInput);
    }

}
