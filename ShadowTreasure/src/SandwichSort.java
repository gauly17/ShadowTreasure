import bagel.util.Point;
import java.util.Comparator;

public class SandwichSort implements Comparator<Sandwich> {

    // Player point variable
    private final Point playerPos;

    /**
     * Constructor is set playerPos
     * @param playerPos Player position
     */
    public SandwichSort(Point playerPos) {
        this.playerPos = playerPos;
    }

    /**
     * Compares the distance values of two sandwiches
     * @param s1 Sandwich 1
     * @param s2 Sandwich 2
     * @return Compared values
     */
    @Override
    public int compare(Sandwich s1, Sandwich s2) {
        return Double.compare(getDistance(s1), getDistance(s2));
    }

    /**
     * Method used in order to measure the distance between a sandwich and player
     * @param sandwich object
     * @return the distance between the player and a sandwich
     */
    public double getDistance(Sandwich sandwich) {
        return Math.sqrt(
                Math.pow(sandwich.getPosition().x - playerPos.x, 2) + Math.pow(sandwich.getPosition().y - playerPos.y, 2));

    }
}
