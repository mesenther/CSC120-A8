/** 
 * Coordinates class implements character's location (and which areas of the map are accessible) and size 
*/
import java.util.HashMap;

public class Coordinates {
    public double x;
    public double y;
    private static HashMap<String, Boolean> accessible = new HashMap<>();

    /**
    * Constructor creates a Coordinate with points used for either character location or size
    */
    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
        setAccessible(-1000, 1000, -1000, 1000, false);
    }

    /** Overloaded constructor; if no coordinates given, assume (0,0) */
    public Coordinates() {
        this(0,0);
    }

    /**
     * Returns coordinate point in (,) form
     */
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    /**
     * Sets an area of the map to be either accessible or unnaccessible
     * @param xmin
     * @param xmax
     * @param ymin
     * @param ymax
     * @param isAccessible
     */
    public void setAccessible(double xmin, double xmax, double ymin, double ymax, boolean isAccessible) {
        for (double i = xmin; i<=xmax; i++) {
            for (double j = ymin; j<=ymax; j++) {
                accessible.put(i + ", " + j, isAccessible);
            }
        }
    }

    /**
     * Determines whether a given coordinate point is accessible
     * @param x
     * @param y
     * @return if point is accessible
     */
    public boolean isAccessible(double x, double y) {
        return accessible.get(x + ", " + y);
    }

    public static void main(String[] args) {
        Coordinates starting = new Coordinates();
        System.out.println(starting.isAccessible(-1000, -1000));

    }
}
