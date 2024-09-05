import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeMasterStopwatch extends JPanel {
    private JLabel stopwatchLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private Timer timer;
    private int elapsedTime; // Time in seconds
    private boolean isRunning; // To check if the stopwatch is running
    private boolean isPaused; // To check if the stopwatch is paused
    private boolean isReset; // To check if the stopwatch is reset

    public TimeMasterStopwatch() {
        stopwatchLabel = new JLabel("Stopwatch: " + "00:00:00", SwingConstants.CENTER);
        stopwatchLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isReset) {
                    elapsedTime = 0; // Reset the time if it's a fresh start
                    isReset = false;
                }
                startStopwatch();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseStopwatch();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetStopwatch();
            }
        });

        setLayout(new BorderLayout());
        add(stopwatchLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resetButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void startStopwatch() {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
                int hours = (elapsedTime / 3600) % 24;
                int minutes = (elapsedTime % 3600) / 60;
                int seconds = elapsedTime % 60;
                stopwatchLabel.setText(String.format("Stopwatch: " + "%02d:%02d:%02d", hours, minutes, seconds));
            }
        });
        timer.start();
        isRunning = true;
        isPaused = false;
    }

    private void pauseStopwatch() {
        if (timer != null && isRunning && !isPaused) {
            timer.stop();
            isPaused = true;
        }
    }

    private void resetStopwatch() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        stopwatchLabel.setText("Stopwatch: 00:00:00");
        elapsedTime = 0;
        isRunning = false;
        isPaused = false;
        isReset = true;
    }
}
