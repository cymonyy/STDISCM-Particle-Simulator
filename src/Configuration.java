//public class Configuration {
//
//    private int method;
//
//    private int x;
//    private int y;
//
//    private double angle;
//
//    private double velocity;
//
//    private int nParticles;
//
//    private int nFinished = 0;
//
//    private double addedValue;
//
//
//    public Configuration(int method, int x, int y, double angle, double velocity, double start, double end, int nParticles) {
//        this.method = method;
//        this.x = x;
//        this.y = y;
//        this.angle = angle;
//        this.velocity = velocity;
//        this.nParticles = nParticles;
//
//        switch (method){
//            case 1: {
//                addedValue = Math.hypot(start, end) / nParticles;
//                break;
//            }
//            case 2, 3: {
//                addedValue = (end - start) / nParticles;
//                break;
//            }
//            default: {
//                addedValue = 0;
//                break;
//            }
//        }
//
//    }
//
//
//    public int getMethod() {
//        return method;
//    }
//
//    public void setMethod(int method) {
//        this.method = method;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
//
//    public double getAngle() {
//        return angle;
//    }
//
//    public void setAngle(double angle) {
//        this.angle = angle;
//    }
//
//    public double getVelocity() {
//        return velocity;
//    }
//
//    public void setVelocity(double velocity) {
//        this.velocity = velocity;
//    }
//
//    public int getnParticles() {
//        return nParticles;
//    }
//
//    public void setnParticles(int nParticles) {
//        this.nParticles = nParticles;
//    }
//
//    public int getnFinished() {
//        return nFinished;
//    }
//
//    public void setnFinished(int nFinished) {
//        this.nFinished = nFinished;
//    }
//
//    public double getAddedValue() {
//        return addedValue;
//    }
//
//    public void setAddedValue(double addedValue) {
//        this.addedValue = addedValue;
//    }
//
//    public void incrementFinished(){
//        this.nFinished++;
//    }
//
//    public boolean isFinished(){
//        return nParticles == nFinished;
//    }
//}
