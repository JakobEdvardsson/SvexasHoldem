package org.svexasHoldem.gui;

import javax.swing.*;
import java.awt.*;

public class TutorialSlideshow extends JDialog {

    private ImageIcon[] slides;
    private int currentIndex;
    private JLabel slideLabel;

    private JButton nextButton;

    private JButton prevButton;

    public TutorialSlideshow(JFrame parent, ImageIcon[] slides) {
        super(parent, "Slideshow", true);
        this.slides = slides;
        this.currentIndex = 0;

        slideLabel = new JLabel();
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        setLayout(new BorderLayout());
        add(slideLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        showSlide();

        prevButton.addActionListener(e -> {
            showPreviousSlide();
            if (currentIndex == 0) {
                prevButton.setEnabled(false);
            }
            // Enable nextButton if currentIndex is not the last slide
            if (currentIndex < slides.length - 1) {
                nextButton.setEnabled(true);
            }
        });

        nextButton.addActionListener(e -> {
            showNextSlide();

            if (currentIndex == slides.length - 1) {
                nextButton.setEnabled(false);
            }
            // Enable prevButton if currentIndex is not the first slide
            if (currentIndex > 0) {
                prevButton.setEnabled(true);
            }
        });



        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void showSlide() {
        slideLabel.setIcon(slides[currentIndex]);
        prevButton.setEnabled(currentIndex != 0);
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
