import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

public class Player extends Entity implements Movement{

    // Variable the sets the players speed
    private static final int STEP_SIZE = 10;
    // Variable assigned to players energy
    private int playerEnergy;
    // Boolean value used to dictate whether the player is in a position to fire a bullet
    private boolean canShot;
    // Boolean value used in regards to whether a bullet has been fired or not
    private boolean hasShot;

    // Variables the assign the font to printed to the game screen
    private final Font font = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();

    /**
     * Create a new player entity which in the main character in the game
     * @param positionX the initial x-position
     * @param positionY the initial y-position
     * @param playerEnergy the initial player energy level
     */
    public Player(double positionX, double positionY, int playerEnergy){
        this.image = new Image("res/images/player.png");
        this.Position = new Point(positionX, positionY);
        this.playerEnergy = playerEnergy;
        this.canShot = true;
        this.hasShot = false;
    }

    /**
     * Return the players energy level
     * @return int playerEnergy
     */
    public int getPlayerEnergy(){
        return this.playerEnergy;
    }

    /**
     * Return the players Point position
     * @return Position
     */
    public Point getPosition(){
        return Position;
    }

    /**
     * Method to Draw the player character
     */
    @Override
    public void render() {
        image.draw(Position.x, Position.y);
        font.drawString("Energy: " + playerEnergy, 20, 760, OPT.setBlendColour(Colour.BLACK));
    }

    /**
     * Method used to set the location for the player to move towards
     * @param dest Destination in which to move towards
     */
    @Override
    public void moveTo(Point dest) {
        this.directionX = dest.x - this.Position.x;
        this.directionY = dest.y - this.Position.y;
        setDirectionTo();
    }

    /**
     * Method used to set the direction of movement
     */
    @Override
    public void setDirectionTo() {
        double Len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= Len;
        this.directionY /= Len;
    }

    /**
     * Method used to lower energy when the player shoots a zombie
     */
    public void shootZombie(){
        if(canShot() && playerEnergy >= 3) {
            playerEnergy -= 3;
            setCanShot(false);
            setHasShot(true);
        }
    }

    /**
     * Method used to set whether the player can shoot or not
     * @param shot boolean value
     */
    public void setCanShot(boolean shot){
        this.canShot = shot;
    }

    /**
     * Method to return whether the player can shoot or not
     * @return canShot
     */
    public boolean canShot(){
        return canShot;
    }

    /**
     * Method used to set whether the player has shot or not
     * @param shot boolean value
     */
    public void setHasShot(boolean shot) {
        this.hasShot = shot;
    }

    /**
     * Method to return whether the player has shot or not
     * @return hasShot
     */
    public boolean hasShot(){
        return hasShot;
    }

    /**
     * Method used to increase the players energy when it eats a sandwich
     */
    public void eatSandwich(){
        playerEnergy += 5;
    }

    /**
     * Method used when the player reaches the treasure to print out a success message
     */
    public void reachTreasure(){
        System.out.println(this.getPlayerEnergy() + ", Success!");
    }

    public void zombiesLeft(){
        System.out.println(this.getPlayerEnergy());
    }

    /**
     * Update method used to update the game in regards to the players movements and actions
     * @param game the game
     */
    public void update(ShadowTreasure game){

        // game.getSandwich() != null is used in cases where there is no initial sandwich in the game
        // If the the player is in range of the zombie, the player shoots the zombie
        if (game.getZombie().inRange(this)) {
            shootZombie();
            // If the player reaches a sandwich, it eats it for energy and the sandwich disappears
        } else if (game.getSandwich() != null && game.getSandwich().meetsPlayer(this)) {
            eatSandwich();
            game.getSandwich().setVisible(false);
            // If the player reaches the treasure, game closes and energy is printed out
        } else if (game.getTreasure().reachesTreasures(this)){
            reachTreasure();
            game.setEndGame(true);
            // If the players energy is lower than 3 and there are zombies left alive but no sandwiches, the game ends
        } else if (game.getZombie().isVisible() && this.canShot() && this.playerEnergy < 3 && game.getSandwich() != null
                && !game.getSandwich().isVisible()){
            zombiesLeft();
            game.setEndGame(true);
        }

        // If there are no zombies left alive, the player moves towards the treasure
        if (!game.getZombie().isVisible()) {
            moveTo(game.getTreasure().getPosition());
            // if there are zombies alive and the player energy is 3 or higher,
            // the player moves towards the closest zombie
        } else if (this.playerEnergy >= 3){
            moveTo(game.getZombie().getPosition());
            // if the players energy is lower than 3 and a sandwich is present,
            // the player will move towards a sandwich
        } else if(game.getSandwich() != null) {
            moveTo(game.getSandwich().getPosition());
        }

        // Move the player in the directions towards the required location
        this.Position = new Point(this.Position.x+STEP_SIZE*this.directionX, this.Position.y+STEP_SIZE*this.directionY);
    }
}
