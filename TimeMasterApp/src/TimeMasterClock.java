import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeMasterClock extends JPanel {
    private JLabel clockLabel24Hour;
    private JLabel clockLabel12Hour;
    private Timer timer;

    public TimeMasterClock() {
        setLayout(new BorderLayout());

        clockLabel24Hour = new JLabel("", SwingConstants.CENTER);
        clockLabel24Hour.setFont(new Font("Serif", Font.PLAIN, 48));
        
        clockLabel12Hour = new JLabel("", SwingConstants.CENTER);
        clockLabel12Hour.setFont(new Font("Serif", Font.PLAIN, 48));
        
        JPanel clocksPanel = new JPanel();
        clocksPanel.setLayout(new GridLayout(2, 1));
        clocksPanel.add(clockLabel24Hour);
        clocksPanel.add(clockLabel12Hour);

        add(clocksPanel, BorderLayout.CENTER);

        timer = new Timer(1000, e -> updateClock());
        timer.start();
    }

    private void updateClock() {
        SimpleDateFormat sdf24Hour = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf12Hour = new SimpleDateFormat("hh:mm:ss a");
        String time24Hour = sdf24Hour.format(new Date());
        String time12Hour = sdf12Hour.format(new Date());
        clockLabel24Hour.setText("24-Hour: " + time24Hour + " H");
        clockLabel12Hour.setText("12-Hour: " + time12Hour);
    }
}
