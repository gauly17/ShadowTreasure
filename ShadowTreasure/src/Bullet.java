import bagel.Image;
import bagel.util.Point;

public class Bullet extends Entity implements Movement{

    // Variables for bullet speed and hit radius
    public static final int BULLET_HIT = 25;
    public static final int BULLET_SPEED = 25;

    /**
     * Constructor for the bullet object in the game
     * @param positionX the initial x-position
     * @param positionY the initial y-position
     */
    public Bullet(double positionX, double positionY){
        this.image = new Image("res/images/shot.png");
        this.Position = new Point(positionX, positionY);
        this.visible = false;
    }

    /**
     * Method used to return the the visible status of the bullet
     * @return boolean visible
     */
    public boolean isVisible(){
        return visible;
    }

    /**
     * Method used to set the visible status of the bullet
     * @param visibility boolean value used to set the visible status of the bullet to true or false
     */
    public void setVisible(boolean visibility){
        this.visible = visibility;
    }

    /**
     * Method used to render the bullet object
     */
    @Override
    public void render() {
        if(visible) {
            image.draw(Position.x, Position.y);
        }
    }

    /**
     * Method to set where the bullet moves to
     * @param dest Destination in which to move towards
     */
    @Override
    public void moveTo(Point dest) {
        this.directionX = dest.x - this.Position.x;
        this.directionY = dest.y - this.Position.y;
        setDirectionTo();
    }

    /**
     * Method to set the direction in which the bullet moves
     */
    @Override
    public void setDirectionTo() {
        double len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= len;
        this.directionY /= len;
    }


    /**
     * Method to return a boolean value once the bullet reaches the zombie
     * @param zombie object within the game the bullet is aiming at
     * @return meetZombie boolean which is true once the bullet hits the zombie
     */
    public boolean hitZombie(Zombie zombie){
        boolean meetZombie = false;
        double distanceToZombie = zombie.getPosition().distanceTo(Position);
        if (distanceToZombie < BULLET_HIT){
            meetZombie = true;
        }
        return meetZombie;
    }

    /**
     * Update method which updates the bullets actions within the game
     * @param game ShadowTreasure game
     */
    public void update(ShadowTreasure game){

        // If the bullet hits the zombie, its visibility as well as the zombie will be set to false
        if(hitZombie(game.getZombie())){
            game.getZombie().setVisible(false);
            this.setVisible(false);
            // Resets the ability of the player to shoot a bullet, so the player can only fire one shot per zombie
            game.getPlayer().setCanShot(true);
            // SetHasShot to false which is used as a condition in the rendering of the bullet
            game.getPlayer().setHasShot(false);
        }

        // If the bullet is visible (been fired by the player), move towards the zombie
        if(visible){
            moveTo(game.getZombie().getPosition());
        }

        // Sets the direction and speed of the bullet
        game.getBullet().Position = new Point(this.Position.x + BULLET_SPEED * this.directionX,
                this.Position.y + BULLET_SPEED * this.directionY);

    }

}
