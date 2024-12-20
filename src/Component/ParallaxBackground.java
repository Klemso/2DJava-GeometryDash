package Component;

import engine.Component;
import engine.GameObject;
import engine.Scene;
import engine.Window;
import engine.LevelEditorScene;
import util.Constants;
import Component.Ground;
import Component.Sprite;
import dataStructure.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ParallaxBackground extends Component {
    public int width, height;
    public Sprite sprite;
    public GameObject[] backgrounds;
    public int timeStep = 0;
    private float speed = 80.0f;

    private Ground ground;
    private boolean followGround;

    public ParallaxBackground(String file, GameObject[] backgrounds, Ground ground, boolean followGround){
        this.sprite = AssetPool.getSprite(file);
        this.width = this.sprite.width;
        this.height = this.sprite.height;
        this.backgrounds = backgrounds;
        this.ground = ground;

        if (followGround) this.speed = Constants.PLAYER_SPEED - 35;
        this.followGround = followGround;
    }

    //     try {
    //         this.image = ImageIO.read(new File(file));
    //     } catch(IOException e) {
    //         e.printStackTrace();
    //     }

    //     this.followGround = followGround;
    //     this.ground = ground;
    // }

    @Override
    public void update(double dt) {
        this.timeStep++;

        this.gameObject.transform.position.x -= dt * speed;
        this.gameObject.transform.position.x = (float)Math.floor(this.gameObject.transform.position.x);
        if (this.gameObject.transform.position.x < -width) {
            float maxX = 0;
            int otherTimeStep = 0;
            for (GameObject go : backgrounds) {
                if (go.transform.position.x > maxX) {
                    maxX = go.transform.position.x;
                    otherTimeStep = go.getComponent(ParallaxBackground.class).timeStep;
                }
            }

            if (otherTimeStep == this.timeStep) {
                this.gameObject.transform.position.x = maxX + width;
            } else {
                this.gameObject.transform.position.x = (float)Math.floor((maxX + width) - (dt * speed));
            }
        }

        if (this.followGround) {
            this.gameObject.transform.position.y = ground.gameObject.transform.position.y;
        }
    }

    @Override
    public Component copy(){
        return null;
    }

    @Override
    public String serialize(int tabSize) {
        return null;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (followGround) {
            g2.drawImage(this.sprite.image, (int)this.gameObject.transform.position.x, (int)(this.gameObject.transform.position.y - Window.getWindow().getCurrentScene().camera.position.y),
                    width, height, null);
        } else {
            int height = Math.min((int)(ground.gameObject.transform.position.y - Window.getWindow().getCurrentScene().camera.position.y), Constants.HEIGHT);
            g2.drawImage(this.sprite.image, (int)this.gameObject.transform.position.x, (int)this.gameObject.transform.position.y, width, Constants.HEIGHT, null);
            g2.setColor(Constants.GROUND_COLOR);
            g2.fillRect((int)this.gameObject.transform.position.x, height, width, Constants.HEIGHT);
        }
    }
}