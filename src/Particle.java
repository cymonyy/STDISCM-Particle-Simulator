import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class Particle {

    private int x;
    private int y;

    private int angle;

    private int velocity;

    private int radius;

    private int red;
    private int green;
    private int blue;


    public Particle() {
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