import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;

public class TimeMasterTimer extends JPanel {
    private JLabel timerLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JButton chooseSoundButton;
    private JTextField timeInput;
    private JComboBox<String> unitSelector;
    private Timer timer;
    private int remainingTime;
    private Clip alertClip;
    private File customAlertSoundFile;

    public TimeMasterTimer() {
        timerLabel = new JLabel("Timer: 00:00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");
        chooseSoundButton = new JButton("Choose Sound");
        timeInput = new JTextField(10);

        // Dropdown for time units
        String[] timeUnits = {"Seconds", "Minutes", "Hours"};
        unitSelector = new JComboBox<>(timeUnits);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseTimer();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
            }
        });

        chooseSoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAlertSound();
            }
        });

        setLayout(new BorderLayout());
        add(timerLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Time:"));
        controlPanel.add(timeInput);
        controlPanel.add(unitSelector);
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resetButton);
        controlPanel.add(chooseSoundButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Load default alert sound
        loadDefaultAlertSound();
    }

    private void loadDefaultAlertSound() {
        try {
            File soundFile = new File("../sounds/alert.wav"); // Default sound path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            alertClip = AudioSystem.getClip();
            alertClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomAlertSound() {
        if (customAlertSoundFile != null) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(customAlertSoundFile);
                alertClip = AudioSystem.getClip();
                alertClip.open(audioInputStream);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    private void chooseAlertSound() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            customAlertSoundFile = fileChooser.getSelectedFile();
            loadCustomAlertSound();
        }
    }

    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
        try {
            remainingTime = Integer.parseInt(timeInput.getText());
            String selectedUnit = (String) unitSelector.getSelectedItem();
            switch (selectedUnit) {
                case "Minutes":
                    remainingTime *= 60;
                    break;
                case "Hours":
                    remainingTime *= 3600;
                    break;
                // Seconds are the default unit, so no conversion needed
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            return;
        }
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingTime <= 0) {
                    timer.stop();
                    playAlertSound();
                    SwingUtilities.invokeLater(() -> timerLabel.setText("Timer: 00:00:00"));
                    return;
                }
                remainingTime--;
                SwingUtilities.invokeLater(() -> {
                    int hours = (remainingTime / 3600) % 24;
                    int minutes = (remainingTime % 3600) / 60;
                    int seconds = remainingTime % 60;
                    timerLabel.setText(String.format("Timer: %02d:%02d:%02d", hours, minutes, seconds));
                });
            }
        });
        timer.start();
    }

    private void pauseTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        timerLabel.setText("Timer: 00:00:00");
        remainingTime = 0;
    }

    private void playAlertSound() {
        if (alertClip != null) {
            alertClip.setFramePosition(0); // Reset sound to the beginning
            alertClip.start(); // Play sound
        }
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Time's up!", "Alert", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
