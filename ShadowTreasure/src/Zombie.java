import bagel.Image;
import bagel.util.Point;

public class Zombie extends Entity{

    /**
     * Constructor for a new Zombie entity
     * @param positionX The initial x-position
     * @param positionY The initial y-position
     */
    public Zombie(double positionX, double positionY){
        this.image = new Image("res/images/zombie.png");
        this.Position = new Point(positionX, positionY);
        this.visible = true;
    }

    /**
     * Method to return the visible status of the zombie, used in regards to whether the zombie is alive or not
     * @return visible; where true when the zombie is alive and false when dead
     */
    public boolean isVisible(){
        return visible;
    }

    /**
     * Method to set the visibility status of the zombie
     * @param visibility boolean
     */
    public void setVisible(boolean visibility){
        this.visible = visibility;
    }

    /**
     * Method that returns a boolean value when the zombie is in range of the player
     * @param player The player
     * @return isInRange when the player is a certain distance from the zombie
     */
    public boolean inRange(Player player) {
        boolean isInRange = false;
        double distanceToPlayer = player.getPosition().distanceTo(Position);
        if (isVisible()){
            if (distanceToPlayer < ShadowTreasure.BULLET_RANGE) {
                isInRange = true;
            }
        }
        return isInRange;
    }

    /**
     * Method used to render the zombie in the game world
     */
    @Override
    public void render() {
        if(visible) {
            image.draw(Position.x, Position.y);
        }
    }

    /**
     * getter method to return the position of a zombie
     * @return zombie position
     */
    public Point getPosition(){
        return Position;
    }
}
