import bagel.Image;
import bagel.Window;

public class Background extends Entity{

    // Assigning the image file which is the background of the game
    private final Image image = new Image("res/images/background.png");

    // Draw the background in the game
    @Override
    public void render() {
        image.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
    }
}
