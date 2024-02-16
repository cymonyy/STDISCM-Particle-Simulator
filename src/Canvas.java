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


public class Canvas extends JPanel {

    protected List<Particle> particles = new ArrayList<>(20);
    private List<Thread> threads = new ArrayList<>(8);

    private List<Wall> walls = new ArrayList<>();

    private DrawCanvas canvas;
    private int canvasWidth = 1280;
    private int canvasHeight = 720;
    public static final int UPDATE_RATE = 60;
    private BlockingQueue<Particle> queue = new LinkedBlockingQueue<>();
    public static int count = 0;

    private final Lock queueLock = new ReentrantLock();
    private final Lock particlesLock = new ReentrantLock();

    private final Lock wallsLock = new ReentrantLock();

    private long lastFrameTime = System.nanoTime();
    private int frameCount = 0;
    private int currentFPS = 0;


    public Canvas() {
        canvas = new DrawCanvas();
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(1280, 720));
        this.setBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 10, 5, false));
        this.add(canvas, BorderLayout.CENTER);

        start();
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
            // Calculate FPS
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastFrameTime;
            if (elapsedTime >= 1_000_000_000) { // If one second has elapsed
                currentFPS = frameCount;
                frameCount = 0;
                lastFrameTime = currentTime;
            } else {
                frameCount++;
            }
            // Draw FPS
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + currentFPS, 10, 20);
        }

        public Dimension getPreferredSize() {
            return (new Dimension(canvasWidth, canvasHeight));
        }
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
                            if (count == particles.size()) {
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


    public void addParticles(List<Particle> particles){
        particlesLock.lock();
        try {
            this.particles.addAll(particles);
            this.queue.addAll(particles);
        } finally {
            particlesLock.unlock();
        }
    }

    public void addWall(Wall wall){
        wallsLock.lock();
        try {
            this.walls.add(wall);
            repaint();
        } finally {
            wallsLock.unlock();
        }
    }

}