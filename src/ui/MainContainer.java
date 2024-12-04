package ui;

import Component.*;
import dataStructure.AssetPool;
import dataStructure.Transform;
import engine.Component;
import engine.GameObject;
import engine.Window;
import util.Constants;
import util.Vector2;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * MainContainer is a UI component responsible for generating and managing menu items in the game's main menu.
 * It handles the initialization, updating, drawing, and serialization of menu items.
 */
public class MainContainer extends Component {
    /**
     * A list of GameObjects representing the menu items displayed in the UI.
     */
    public List<GameObject> menuItems;

    /**
     * Constructs a MainContainer and initializes its list of menu items.
     */
    public MainContainer() {
        this.menuItems = new ArrayList<>();
        init();
    }

    /**
     * Initializes the MainContainer by loading sprites from the asset pool and creating GameObjects
     * for each menu item. These objects are positioned and given the appropriate components such as
     * MenuItem and BoxBounds.
     */
    public void init(){
        Spritesheet groundSprites = AssetPool.getSpritesheet("assets/groundSprites.png");
        Spritesheet buttonSprites = AssetPool.getSpritesheet("assets/ui/buttonSprites.png");

        // Generate menu items based on sprite sheet data
        for (int i = 0; i < groundSprites.sprites.size(); i++) {
            Sprite currentSprite = groundSprites.sprites.get(i);
            int x = Constants.BUTTON_OFFSET_X + (currentSprite.column * Constants.BUTTON_WIDTH) +
                    (currentSprite.column * Constants.BUTTON_SPACING_HZ);
            int y = Constants.BUTTON_OFFSET_Y + (currentSprite.row * Constants.BUTTON_HEIGHT) +
                    (currentSprite.row * Constants.BUTTON_SPACING_VT);

            GameObject obj = new GameObject("Generated", new Transform(new Vector2(x, y)), -1);
            obj.addComponent(currentSprite.copy());
            MenuItem menuItem = new MenuItem(x, y, Constants.BUTTON_HEIGHT, Constants.BUTTON_WIDTH,
                    buttonSprites.sprites.get(0), buttonSprites.sprites.get(1));
            obj.addComponent(menuItem);
            obj.addComponent(new BoxBounds(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
            menuItems.add(obj);
        }
    }

    /**
     * Starts all components of the menu items. This method is called when the component is initialized.
     */
    @Override
    public void start() {
        for (GameObject g : menuItems) {
            for (Component c : g.getAllComponents()) {
                c.start();
            }
        }
    }

    /**
     * Updates all menu items. This method is called each frame to update the state of the menu.
     * 
     * @param deltaTime The time elapsed since the last frame, in seconds.
     */
    @Override
    public void update(double deltaTime) {
        for (GameObject g : this.menuItems) {
            g.update(deltaTime);
        }
    }

    /**
     * Creates a copy of the MainContainer component. In this case, it returns null as cloning behavior
     * is not implemented.
     * 
     * @return null since cloning is not implemented.
     */
    @Override
    public Component copy() {
        return null;
    }

    /**
     * Draws all the menu items to the screen.
     * 
     * @param g2 The Graphics2D object used for drawing.
     */
    @Override
    public void draw(Graphics2D g2) {
        for (GameObject g : this.menuItems) {
            g.draw(g2);
        }
    }

    /**
     * Serializes the MainContainer component to a string representation.
     * This implementation returns an empty string as serialization is not yet implemented.
     * 
     * @param tabSize The number of tabs to use for indentation in the serialized output.
     * @return An empty string.
     */
    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
