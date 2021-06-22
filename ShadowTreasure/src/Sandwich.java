import bagel.Image;
import bagel.util.Point;

public class Sandwich extends Entity{

    /**
     * Create a new sandwich object which the player eats for energy
     * @param positionX the initial x-position
     * @param positionY the initial y-position
     */
    public Sandwich(double positionX, double positionY){
        this.image = new Image("res/images/sandwich.png");
        this.Position = new Point(positionX, positionY);
        this.visible = true;
    }

    /**
     * Method to return the visibility of the sandwich object
     * @return visible boolean
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Method to set the visibility of the sandwich object, used when eaten by the player
     * @param visibility boolean
     */
    public void setVisible(boolean visibility) {
        this.visible = visibility;
    }

    /**
     * Method used to return a boolean value when the player reaches the sandwich
     * @param player character in the game world
     * @return metPlayer boolean
     */
    public boolean meetsPlayer(Player player) {
        boolean metPlayer = false;
        if (isVisible()){
            double distanceToPlayer = player.getPosition().distanceTo(Position);
            if (distanceToPlayer < ShadowTreasure.MEET_DISTANCE) {
                metPlayer = true;
            }
        }
        return metPlayer;
    }

    /**
     * Method used to render the sandwich in game
     */
    @Override
    public void render() {
        if(visible) {
            image.draw(Position.x, Position.y);
        }
    }

    /**
     * Return the position of the sandwich
     * @return Position
     */
    public Point getPosition(){
        return Position;
    }
}
