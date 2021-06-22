import bagel.Image;
import bagel.util.Point;

public abstract class Entity{

    // Variable used in regard to setting entity direction
    protected double directionX, directionY;

    // Variable used to dictate the Point location of an entity
    protected Point Position;

    // Variable used for the x and y positions of entities
    protected double positionX, positionY;

    // Variable used to regards to setting entities visibility in the game world
    protected Boolean visible;

    // Image variable used in regards to setting an entities visual appearance
    protected Image image;

    // An abstract method used by all subclasses in their drawing into the game world
    protected abstract void render();

    /**
     * Method used by entities to set their position
     * @param Position the Point of the entities
     */
    public void setPosition(Point Position) {
        this.Position = new Point(positionX, positionY);
    }

    /**
     * Method to return the Point position of an entity
     * @return the Point position
     */
    public Point getPosition(){
        return Position;
    }

}
