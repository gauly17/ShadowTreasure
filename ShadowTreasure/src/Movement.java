import bagel.util.Point;

interface Movement {

    /**
     * Abstract method used in order to move to the required Point
     * @param dest Destination in which to move towards
     */
    void moveTo(Point dest);

    /**
     * Abstract method used in order to set direction of movement.
     */
    void setDirectionTo();
}
