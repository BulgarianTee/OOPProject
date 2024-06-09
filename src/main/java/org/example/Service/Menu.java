package org.example.Service;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Menu extends JPanel {

    private final Image backgroundImg;
    private final Image titleImg;
    private final Image inputImg;

    public Menu() {
        setPreferredSize(new Dimension(WindowUtils.windowWidth, WindowUtils.windowHeight));

        // load images
        this.backgroundImg = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/background.png")
        )).getImage();

        this.titleImg = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/title.png")
        )).getImage();

        this.inputImg = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/inputMsg.png")
        )).getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, WindowUtils.windowWidth, WindowUtils.windowHeight, null);
        g.drawImage(titleImg, WindowUtils.windowWidth/2-395, WindowUtils.windowHeight/8, 790, 85, null);
        g.drawImage(inputImg, 0, WindowUtils.windowHeight*3/4, 784, 119, null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }
}
