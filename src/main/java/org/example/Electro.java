import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Electro extends JPanel implements Runnable {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;
    private static final int DELAY = 5; // Milliseconds between actualizations
    private static final int SCALE_FACTOR = 2;
    private static final int AMPLITUDE = 50;
    private int[] ecgData;
    private int dataIndex;

    public Electro() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        ecgData = new int[WIDTH / SCALE_FACTOR];
        dataIndex = 0;
        new Thread(this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);

        int lastX = 0, lastY = HEIGHT / 2;

        for (int i = 0; i < WIDTH / SCALE_FACTOR; i++) {
            int x = i * SCALE_FACTOR;
            int y = lastY - ecgData[i];
            g.drawLine(lastX, lastY, x, y);
            lastX = x;
            lastY = y;
        }
    }

    @Override
    public void run() {
        while (true) {
            generateData();
            repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateData() {
        // aleatory simulation of data between AMPLITUDE and AMPLITUDE
        Random random = new Random();
        int newData = random.nextInt(AMPLITUDE * 2) - AMPLITUDE;

        // Adding new data to the ECG
        ecgData[dataIndex] = newData;
        dataIndex = (dataIndex + 1) % (WIDTH / SCALE_FACTOR);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Electro");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new Electro());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
