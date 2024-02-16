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

public class BouncingBalls extends JPanel implements MouseListener {
    protected List<Ball> balls = new ArrayList<>(20);
    private List<Thread> threads = new ArrayList<>(8);

    private List<Wall> walls = new ArrayList<>();

    private DrawCanvas canvas;
    private int canvasWidth;
    private int canvasHeight;
    public static final int UPDATE_RATE = 60;
    private BlockingQueue<Ball> queue = new LinkedBlockingQueue<>();
    public static int count = 0;

    private final Lock queueLock = new ReentrantLock();
    private final Lock ballsLock = new ReentrantLock();

    public BouncingBalls(int width, int height) {
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
                            Ball ball = queue.take();
                            ball.move(walls);
                            queue.add(ball);
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
            Ball ball = new Ball();
            balls.add(ball);
            queue.add(ball);
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
            for (Ball ball : balls) {
                ball.draw(g);
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
            f.setContentPane(new BouncingBalls(1280, 720));
            f.pack();
            f.setVisible(true);
        });
    }

    public static class Ball {

        private int x;
        private int y;

        private int angle;

        private int velocity;

        private int radius;

        private int red;
        private int green;
        private int blue;


        public Ball() {
            this.x = random(1280);
            this.y = random(720);
            this.angle = random(360);
            this.velocity = 2 + random(30);
            this.radius = random(20);
            this.red = random(255);
            this.green = random(255);
            this.blue = random(255);
        }


        public void draw(Graphics g) {
            g.setColor(new Color(red, green, blue));
            g.fillOval(x, 720 - y, 10, 10);
        }
        /* */
        public void move(List<Wall> walls) {
            int nextX = x + (int) (velocity * Math.cos(Math.toRadians(angle)));
            int nextY = y + (int) (velocity * Math.sin(Math.toRadians(angle)));
        
            // Check for collision with walls
            for (Wall wall : walls) {
                if (wall.willCollide(x, y, nextX, nextY)) {
                    double dx = nextX - x;
                    double dy = nextY - y;
                    double wallAngle = Math.atan2(wall.getQ2().y - wall.getQ1().y, wall.getQ2().x - wall.getQ1().x);
        
                    // Calculate angle of reflection
                    double incidentAngle = Math.atan2(dy, dx);
                    double reflectionAngle = 2 * wallAngle - incidentAngle;
                    while (reflectionAngle < 0) {
                        reflectionAngle += 2 * Math.PI;
                    }
                    while (reflectionAngle > 2 * Math.PI) {
                        reflectionAngle -= 2 * Math.PI;
                    }
        
                    // Update ball angle
                    angle = (int) Math.toDegrees(reflectionAngle);
        
                    // Move the ball to the point of collision
                    x = (int) (x + Math.cos(reflectionAngle) * velocity);
                    y = (int) (y + Math.sin(reflectionAngle) * velocity);
        
                    break;
                }
            }
        
            // Check for collision with screen boundaries and reflect if necessary
            if (x <= 0 || x >= 1280) {
                angle = 180 - angle;
            }
            if (y <= 0 || y >= 720) {
                angle = -angle;
            }
        
            // Move the ball
            x += (int) (velocity * Math.cos(Math.toRadians(angle)));
            y += (int) (velocity * Math.sin(Math.toRadians(angle)));
        }
        
        // public void move(List<Wall> walls) {
        //     int nextX = x + (int) (velocity * Math.cos(Math.toRadians(angle)));
        //     int nextY = y + (int) (velocity * Math.sin(Math.toRadians(angle)));
        
        //     boolean collided = false; // Flag to track if a collision occurred
        //     // Check for collision with walls
        //     for (Wall wall : walls) {
        //         if (wall.willCollide(x, y, nextX, nextY)) {
        //             double dx = nextX - x;
        //             double dy = nextY - y;
        //             double wallAngle = Math.atan2(wall.q2.y - wall.q1.y, wall.q2.x - wall.q1.x);
        //             double angleBetween = Math.atan2(dy, dx) - wallAngle;
        
        //             // Reflect the velocity only if the ball is approaching the wall from the outside
        //             if (angleBetween < -Math.PI / 2 || angleBetween > Math.PI / 2) {
        //                 double incidentAngle = Math.atan2(dy, dx); // Angle of incidence
        //                 double reflectionAngle = 2 * wallAngle - incidentAngle; // Angle of reflection
        //                 angle = (int) Math.toDegrees(reflectionAngle); // Convert to degrees
        //                 collided = true; // Set collision flag
        //                 System.out.println("collided");
        //             }
        
        //             break; 
        //         }
        //     }
        //     // If no collision with walls, move the ball based on its velocity and angle
        //     if (!collided) {
        //         x = nextX;
        //         y = nextY;
        
        //         // Check for collision with screen boundaries and reflect if necessary
        //         if (x <= 0 || x >= 1280) {
        //             angle = 180 - angle;
        //         }
        //         if (y <= 0 || y >= 720) {
        //             angle = -angle;
        //         }
        //     }
        // }

        public static int random(int maxRange) {
            return (int) Math.round((Math.random() * maxRange));
        }
    }

    public static class Wall {

        private final Point q1;

        private final Point q2;


        public Wall(int startX, int startY, int endX, int endY) {

            this.q1 = new Point(startX, startY);
            this.q2 = new Point(endX, endY);
        }

        public void draw(Graphics g) {
            g.setColor(Color.black);
//            g.fillOval(q1.x, 720 - q1.y, 20, 20);
//            g.fillOval(q2.x, 720 - q2.y, 20, 20);

            g.drawLine(q1.x, 720 - q1.y, q2.x, 720 - q2.y);
        }

        private static class Point {
            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

        }

        ;

        // Given three collinear points p, q, r, the function checks if
        // point q lies on line segment 'pr'
        private boolean onSegment(Point p, Point q, Point r) {
            return q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                    q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y);
        }

        // To find orientation of ordered triplet (p, q, r).
        // The function returns following values
        // 0 --> p, q and r are collinear
        // 1 --> Clockwise
        // 2 --> Counterclockwise

        private int orientation(Point p, Point q, Point r) {
            // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
            // for details of below formula.
            int val = (q.y - p.y) * (r.x - q.x) -
                    (q.x - p.x) * (r.y - q.y);

            if (val == 0) return 0; // collinear

            return (val > 0) ? 1 : 2; // clock or counterclock wise
        }

        // The main function that returns true if line segment 'p1q1'
        // and 'p2q2' intersect.

        private boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
            // Find the four orientations needed for general and
            // special cases
            int o1 = orientation(p1, q1, p2);
            int o2 = orientation(p1, q1, q2);
            int o3 = orientation(p2, q2, p1);
            int o4 = orientation(p2, q2, q1);

            // General case
            if (o1 != o2 && o3 != o4)
                return true;

            // Special Cases
            // p1, q1 and p2 are collinear and p2 lies on segment p1q1
            if (o1 == 0 && onSegment(p1, p2, q1)) return true;

            // p1, q1 and q2 are collinear and q2 lies on segment p1q1
            if (o2 == 0 && onSegment(p1, q2, q1)) return true;

            // p2, q2 and p1 are collinear and p1 lies on segment p2q2
            if (o3 == 0 && onSegment(p2, p1, q2)) return true;

            // p2, q2 and q1 are collinear and q1 lies on segment p2q2
            if (o4 == 0 && onSegment(p2, q1, q2)) return true;

            return false; // Doesn't fall in any of the above cases
        }

        public boolean willCollide(int startX, int startY, int endX, int endY) {
            Point p1 = new Point(startX, startY);
            Point p2 = new Point(endX, endY);

            return doIntersect(p1, p2, this.q1, this.q2);
        }

        public Point getQ1() {
            return q1;
        }

        public Point getQ2() {
            return q2;
        }
    }
}
