import bagel.util.Point;

import java.util.Comparator;

public class ZombieSort implements Comparator<Zombie> {

    // player position variable
    private final Point playerPos;

    /**
     * Constructor for the player pos
     * @param playerPos position
     */
    public ZombieSort(Point playerPos) {
        this.playerPos = playerPos;
    }

    /**
     * Compares the distance values of two zombies
     * @param z1 zombie 1
     * @param z2 zombie 2
     * @return the compared distance
     */
    @Override
    public int compare(Zombie z1, Zombie z2) {
        return Double.compare(getDistance(z1), getDistance(z2));
    }

    /**
     * Method used in order to measure the distance between a sandwich and player
     * @param zombie object
     * @return the distance between the player and a zombie
     */
    public double getDistance(Zombie zombie) {
        return Math.sqrt(
                Math.pow(zombie.getPosition().x - playerPos.x, 2) + Math.pow(zombie.getPosition().y - playerPos.y, 2));

    }
}
