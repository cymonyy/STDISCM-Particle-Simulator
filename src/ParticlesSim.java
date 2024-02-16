import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParticlesSim extends JPanel implements MouseListener {
    protected List<Particle> particles = new ArrayList<>(20);
    private List<Thread> threads = new ArrayList<>(8);

    private List<Wall> walls = new ArrayList<>();

    private DrawCanvas canvas;
    private int canvasWidth;
    private int canvasHeight;
    public static final int UPDATE_RATE = 60;
    private BlockingQueue<Particle> queue = new LinkedBlockingQueue<>();
    public static int count = 0;

    private final Lock queueLock = new ReentrantLock();
    private final Lock ballsLock = new ReentrantLock();

    public ParticlesSim(int width, int height) {
        canvasWidth = width;
        canvasHeight = height;
        canvas = new DrawCanvas();
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        this.addMouseListener(this);

        // Add some diagonal walls
        walls.add(new Wall(100, 500, 500, 700));
        walls.add(new Wall(500, 300, 200, 100));
        walls.add(new Wall(600, 300, 300, 200));


        start();
    }

    public void start() {
        for (int i = 0; i < 8; i++) {
            Thread t = new Thread(() -> {
                while (true) {
                    if(!queue.isEmpty()){
                        queueLock.lock();
                        try {
                            Particle particle = queue.take();
                            particle.move(walls);
                            queue.add(particle);
                            count++;
                            if (count == queue.size()) {
                                count = 0;
                                repaint();
                                Thread.sleep(1000 / UPDATE_RATE);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            queueLock.unlock();
                        }
                    }
                }
            });
            threads.add(t);
            t.start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ballsLock.lock();
        try {
            Particle particle = new Particle();
            particles.add(particle);
            queue.add(particle);
        } finally {
            ballsLock.unlock();
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    class DrawCanvas extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Particle particle : particles) {
                particle.draw(g);
            }
            for (Wall wall : walls) {
                wall.draw(g);
            }
        }

        public Dimension getPreferredSize() {
            return (new Dimension(canvasWidth, canvasHeight));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Bouncing Balls");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new ParticlesSim(1280, 720));
            f.pack();
            f.setVisible(true);
        });
    }
}
