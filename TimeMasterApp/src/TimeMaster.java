import javax.swing.*;

public class TimeMaster {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("TimeMaster");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(585, 400);

            ImageIcon icon = new ImageIcon("../sprites/clock.png");
            frame.setIconImage(icon.getImage());

            // Create a tabbed pane
            JTabbedPane tabbedPane = new JTabbedPane();

            // Add tabs for the stopwatch, clock, and timer
            tabbedPane.addTab("Stopwatch", new TimeMasterStopwatch());
            tabbedPane.addTab("Clock", new TimeMasterClock());
            tabbedPane.addTab("Timer", new TimeMasterTimer());

            // Add the tabbed pane to the frame
            frame.add(tabbedPane);

            frame.setVisible(true);
        });
    }
}
