import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class Particle {
    private int x;
    private int y;

    private double angle;

    private double velocity;

    private int red;
    private int green;
    private int blue;

    public Particle(int x, int y, double angle, double velocity) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;

        this.red = random(225);
        this.green = random(225);
        this.blue = random(225);
        
    }


    public void draw(Graphics g) {
        g.setColor(new Color(red, green, blue));
        g.fillOval(x, 720 - y, 10, 10);
    }

    public void move(List<Wall> walls) {
        int nextX = x + (int) (velocity * Math.cos(Math.toRadians(angle)));
        int nextY = y + (int) (velocity * Math.sin(Math.toRadians(angle)));

        // Check for collision with walls
        for (Wall wall : walls) {
            if (wall.willCollide(x, y, nextX, nextY)) {
                double reflectionAngle = getReflectionAngle(wall, nextX, nextY);

                // Update ball angle
                angle = Math.toDegrees(reflectionAngle);

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

    private double getReflectionAngle(Wall wall, int nextX, int nextY) {
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
        return reflectionAngle;
    }

    public static int random(int maxRange) {
        return (int) Math.round((Math.random() * maxRange));
    }

}
