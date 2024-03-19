package org.svexasHoldem.gui;

import javax.swing.*;
import java.awt.*;

public class TutorialSlideshow extends JDialog {

    private ImageIcon[] slides;
    private int currentIndex;
    private JLabel slideLabel;

    public TutorialSlideshow(JFrame parent, ImageIcon[] slides) {
        super(parent, "Slideshow", true);
        this.slides = slides;
        this.currentIndex = 0;

        slideLabel = new JLabel();
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");

        setLayout(new BorderLayout());
        add(slideLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        showSlide();

        prevButton.addActionListener(e -> showPreviousSlide());

        nextButton.addActionListener(e -> showNextSlide());

        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void showSlide() {
        slideLabel.setIcon(slides[currentIndex]);
    }

    private void showPreviousSlide() {
        currentIndex = (currentIndex - 1 + slides.length) % slides.length;
        showSlide();
    }

    private void showNextSlide() {
        currentIndex = (currentIndex + 1) % slides.length;
        showSlide();
    }
    }
