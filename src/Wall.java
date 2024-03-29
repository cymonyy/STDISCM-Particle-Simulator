import java.awt.Color;
import java.awt.Graphics;

public class Wall {

        private final Point q1;

        private final Point q2;


        public Wall(int startX, int startY, int endX, int endY) {

            this.q1 = new Point(startX, startY);
            this.q2 = new Point(endX, endY);
        }

        public void draw(Graphics g) {
            g.setColor(Color.black);

            g.drawLine(q1.x, 720 - q1.y, q2.x, 720 - q2.y);
        }

        static class Point {
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