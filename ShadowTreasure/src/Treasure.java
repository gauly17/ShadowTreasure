import bagel.Image;
import bagel.util.Point;

public class Treasure extends Entity{

    /**
     * Create a new Treasure object
     * @param positionX the initial x-position
     * @param positionY the initial y-position
     */
    public Treasure(double positionX, double positionY){
        this.image = new Image("res/images/treasure.png");
        this.Position = new Point(positionX, positionY);
    }

    /**
     * Method that returns a boolean value when the player reaches the treasure
     * @param player The player in the game world
     * @return hasReached boolean value when the player reaches the treasure
     */
    public boolean reachesTreasures(Player player) {
        boolean hasReached = false;
        double distanceToPlayer = player.getPosition().distanceTo(Position);
        if (distanceToPlayer < ShadowTreasure.MEET_DISTANCE) {
            hasReached = true;
        }
        return hasReached;
    }

    /**
     * Method used to render the treasure in the game world
     */
    @Override
    public void render() {
        image.draw(Position.x, Position.y);
    }
}
