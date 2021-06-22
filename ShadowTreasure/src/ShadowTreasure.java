import bagel.*;
import bagel.util.Point;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


/**
 * ShadowTreasure project 2 - A bagel game where the player must battle through hordes of zombies
 * in order to reach the treasure.
 *
 * By Brody Gaul
 * Student ID: 1240820
 * Class: SWEN20003
 * University of Melbourne
 *
 * last updated: 26/05/2021
 */
public class ShadowTreasure extends AbstractGame {

    // Game tick variable
    protected int tick;
    // ArrayList used for storing Zombie objects
    ArrayList<Zombie> zombies;
    // Arraylist used for storing Sandwich objects
    ArrayList<Sandwich> sandwiches;
    // Arraylist used for storing Player objects
    ArrayList<Player> players;
    // Arraylist used for storing Treasure objects
    ArrayList<Treasure> treasures;

    // Variable for the player meet distance with an entity
    public static final int MEET_DISTANCE = 50;
    // Variable for the bullet range
    public static final int BULLET_RANGE = 150;

    // Game objects used
    Bullet bullet;
    Sandwich sandwich;
    Player player;
    Zombie zombie;
    Treasure treasure;

    // Boolean variable used to end the game
    protected boolean endOfGame;

    // for rounding double number; use this to print the location of the player
    protected static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Zombie getter
     * @return zombie
     */
    public Zombie getZombie(){
        return zombie;
    }

    /**
     * Player getter
     * @return player
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Sandwich getter
     * @return sandwich
     */
    public Sandwich getSandwich(){
        return sandwich;
    }

    /**
     * Treasure getter
     * @return treasure
     */
    public Treasure getTreasure(){
        return treasure;
    }

    /**
     * Bullet getter
     * @return bullet
     */
    public Bullet getBullet(){
        return bullet;
    }

    /**
     * Method used to set the bullets position to that of the player
     * @return bullet position
     */
    public Point setBulletPos(){
        return new Point(getPlayer().getPosition().x, getPlayer().getPosition().y);
    }

    /**
     * Method used to set the end of game status
     * @param endOfGame boolean
     */
    public void setEndGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
    }

    /**
     * Removes a sandwich from the sandwiches arraylist
     * @param sandwich object
     */
    public void removeSandwich(Sandwich sandwich){
        sandwiches.remove(sandwich);
    }

    /**
     * Removes a zombie from the zombies array list
     * @param zombie object
     */
    public void removeZombie(Zombie zombie){
        zombies.remove(zombie);
    }

    /**
     * Method used to initialise a new zombie once one is killed by the player
     */
    public void zombieSpawn(){
        // Sort zombies by distance to the player, so the closest zombie is targeted next
        sortZombies(zombies, player);
        // Iterate through the zombies arraylist and select the next zombie
        for(int i = 0; i < zombies.size(); i++){
            zombie = zombies.get(i);
            // If a zombie is killed, its removed from the arraylist
            if(!zombie.isVisible()){
                removeZombie(zombie);
            }
            // Select the next zombie
            if(zombies.size() != 0){
                this.zombie = zombies.get(0);
            }
        }
    }

    /**
     * Method to the sort the zombies via distance to the player
     * @param zombies game object
     * @param player game object
     */
    public void sortZombies(ArrayList<Zombie> zombies, Player player) {
        Point playerPos = new Point(player.getPosition().x, player.getPosition().y);
        Collections.sort(zombies, new ZombieSort(playerPos));
    }

    /**
     * Method used to initialise a new sandwich once one is eaten by the player
     */
    public void sandwichSpawn(){
        // Sort sandwiches by distance to the player, so the closest sandwich is targeted next
        sortSandwiches(sandwiches, player);
        // Iterate through the sandwiches arraylist and select the next sandwich
        for (int i = 0; i < sandwiches.size(); i++){
            sandwich = sandwiches.get(i);
            // If a sandwich is eaten, its removed from the arraylist
            if(!sandwich.isVisible()){
                removeSandwich(sandwich);
            }
            // Select the next sandwich
            if(sandwiches.size() != 0){
                this.sandwich = sandwiches.get(0);
            }
        }
    }

    /**
     * Method to the sort the sandwiches via distance to the player
     * @param sandwiches object
     * @param player object
     */
    public void sortSandwiches(ArrayList<Sandwich> sandwiches, Player player) {
        Point playerPos = new Point(player.getPosition().x, player.getPosition().y);
        Collections.sort(sandwiches, new SandwichSort(playerPos));
    }

    /**
     * Method used to initialize attributes as needed
     * @throws IOException if file is missing
     */
    public ShadowTreasure() throws IOException {
        this.loadEnvironment();
        // Add code to initialize other attributes as needed
        this.tick = 1;
        this.endOfGame = false;

        // Buffered writer to clear the output csv file at the start of each program
        try {
            BufferedWriter clear = new BufferedWriter(new FileWriter("res/IO/output.csv", false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load from input file
     */
    private void loadEnvironment(){

        zombies = new ArrayList<>();
        sandwiches = new ArrayList<>();
        players = new ArrayList<>();
        treasures = new ArrayList<>();

        // Read from the file and set up the environment
        try (BufferedReader reader = new BufferedReader(new FileReader("res/IO/environment.csv"))){
            String data;
            while ((data = reader.readLine()) != null) {
                String[] dataPart = data.split(",");
                String type = dataPart[0];
                type = type.replaceAll("[^a-zA-Z0-9]", ""); // Remove special characters
                double positionX = Double.parseDouble(dataPart[1]);
                double positionY = Double.parseDouble(dataPart[2]);

                // Assign objects to their associated arraylist
                if (type.contains("Player")) {
                    this.player = new Player(positionX, positionY, Integer.parseInt(dataPart[3]));
                    players.add(player);
                } else if (type.contains("Zombie")) {
                    this.zombie = new Zombie(positionX, positionY);
                    zombies.add(zombie);
                } else if (type.contains("Treasure")) {
                    this.treasure = new Treasure(positionX, positionY);
                    treasures.add(treasure);
                } else if (type.contains("Sandwich")) {
                    this.sandwich = new Sandwich(positionX, positionY);
                    sandwiches.add(sandwich);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        this.bullet = new Bullet(setBulletPos().x, setBulletPos().y);
    }

    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {
        // Logic to update the game, as per specification goes here
        // Render a new background
        Background background = new Background();
        background.render();

        // Render the player
        player.render();
        // Render the bullet
        bullet.render();

        // Spawn new sandwich
        sandwichSpawn();
        // Spawn new zombie
        zombieSpawn();

        // Sets the bullet position to the players position if the player can shoot
        if(player.canShot()){
            this.bullet = new Bullet(player.getPosition().x, player.getPosition().y);
        }

        // if the player has shot a bullet, set the bullets visibility to true
        if(player.hasShot()){
            bullet.setVisible(true);
        }

        // Render the zombie's in the zombies arraylist
        for (Zombie zombie : zombies){
            zombie.render();
        }

        // Render the sandwiches in the sandwiches arraylist
        for (Sandwich sandwich : sandwiches){
            sandwich.render();
        }

        // Render the treasure in the treasures arraylist
        for (Treasure treasure : treasures){
            treasure.render();
        }

        // Update the game in accordance with each tick
        if(this.endOfGame || input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        } else {
            int TICK_RATE = 10;
            if (tick > TICK_RATE) {
                bullet.update(this);
                player.update(this);
                tick = 1;

                // Print out the bullets output in each tick
                if (getBullet().isVisible()){
                    System.out.println(df.format(bullet.getPosition().x) + ", " + df.format(bullet.getPosition().y));

                    // Write the bullets output to the output.csv file
                    String data = String.valueOf(df.format(bullet.getPosition().x) + "," + df.format(bullet.getPosition().y));
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("res/IO/output.csv", true));
                        writer.write(data);
                        writer.write("\n");
                        writer.flush();
                        writer.close();
                    }
                    catch(Exception e) {
                        e.getStackTrace();
                    }
                }
            }
            tick++;
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }
}
