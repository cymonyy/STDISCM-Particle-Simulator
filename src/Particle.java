public class Particle {
    private int x;
    private int y;

    private int angle;

    private int velocity;

    public Particle(int x, int y, int angle, int velocity) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void move(){
        x += (int) ((int) velocity * Math.cos(angle));
        y += (int) ((int) velocity * Math.sin(angle));
    }


}
