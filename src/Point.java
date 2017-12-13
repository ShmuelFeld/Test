/**
 * Created by Sharon on 09 דצמבר 2017.
 */
public class Point {
    private int x;
    private int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof Point)) {
            return false;
        }
        Point p = (Point) obj;
        return Integer.compare(this.x, p.getX()) == 0 && Integer.compare(this.y, p.getY()) == 0;
    }
}
