package Component;

import engine.Component;
import util.Constants;
import util.Vector2;
import engine.GameObject;
import engine.Window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;


/**
 * Classe {@code Player} qui représente un joueur dans le jeu.
 * Hérite de la classe {@code Component}.
 */
public class Player extends Component {

    /** Sprite pour la première couche. */
    Sprite layerOne, layerTwo, layerThree;
    
    /** Largeur du joueur. */
    public int width, height;
    
    /** Couleurs utilisées pour les différentes couches. */
    private Color colorOne, colorTwo;
    
    /** Indique si le joueur est au sol. */
    public boolean onGround = true;

    /**
     * Constructeur de la classe {@code Player}.
     *
     * @param layerOne Sprite pour la première couche
     * @param layerTwo Sprite pour la deuxième couche
     * @param layerThree Sprite pour la troisième couche
     * @param colorOne Première couleur à appliquer
     * @param colorTwo Deuxième couleur à appliquer
     */
    public Player(Sprite layerOne, Sprite layerTwo, Sprite layerThree,
                  Color colorOne, Color colorTwo){
        this.width = Constants.PLAYER_WIDTH;
        this.height = Constants.PLAYER_HEIGHT;
        this.layerOne = layerOne;
        this.layerTwo = layerTwo;
        this.layerThree = layerThree;
        this.colorOne = colorOne;
        this.colorTwo = colorTwo;

        int threshold = 200;
        for (int y=0; y < layerOne.image.getWidth(); y++) {
            for (int x=0; x < layerOne.image.getHeight(); x++) {
                Color color = new Color(layerOne.image.getRGB(x, y));
                if (color.getRed() > threshold && color.getGreen() > threshold && color.getBlue() > threshold) {
                    layerOne.image.setRGB(x, y, colorOne.getRGB());
                }
            }
        }

        for (int y=0; y < layerTwo.image.getWidth(); y++) {
            for (int x=0; x < layerTwo.image.getHeight(); x++) {
                Color color = new Color(layerTwo.image.getRGB(x, y));
                if (color.getRed() > threshold && color.getGreen() > threshold && color.getBlue() > threshold) {
                    layerTwo.image.setRGB(x, y, colorTwo.getRGB());
                }
            }
        }
    }

    /**
     * Met à jour l'état du joueur à chaque frame.
     *
     * @param deltaTime Temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(double deltaTime) {
        if (onGround && Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            addJumpForce();
            this.onGround = false;
        }
    
        if (!onGround) {
            gameObject.transform.rotation += 12.5f * deltaTime;
        } else {
            gameObject.transform.rotation = (int)gameObject.transform.rotation % 360;
            if (gameObject.transform.rotation > 180 && gameObject.transform.rotation < 360) {
                gameObject.transform.rotation = 0;
            } else if (gameObject.transform.rotation > 0 && gameObject.transform.rotation < 180) {
                gameObject.transform.rotation = 0;
            }
        }
    }

    /**
     * Ajoute une force de saut au joueur.
     */
    private void addJumpForce() {
        gameObject.getComponent(RigidBody.class).velocity.y = Constants.JUMP_FORCE;
    }

    /**
     * Gère la mort du joueur en réinitialisant sa position et la caméra.
     */
    public void die() {
        gameObject.transform.position.x = 0;
        gameObject.transform.position.y = 30;
        Window.getWindow().getCurrentScene().camera.position.x = 0;
    }

    /**
     * Dessine le joueur à l'écran.
     *
     * @param g2 Contexte graphique utilisé pour le dessin
     */
    @Override
    public void draw(Graphics2D g2){
        AffineTransform transform = new AffineTransform();
        transform.setToIdentity();
        transform.translate(gameObject.transform.position.x, gameObject.transform.position.y);
        transform.rotate(gameObject.transform.rotation, width * gameObject.transform.scale.x / 2, height* gameObject.transform.scale.y /2);
        transform.scale(gameObject.transform.scale.x, gameObject.transform.scale.y);
        g2.drawImage(layerOne.image, transform, null);
        g2.drawImage(layerTwo.image, transform, null);
        g2.drawImage(layerThree.image, transform, null);
    }

    /**
     * Crée une copie de ce composant.
     *
     * @return Une nouvelle instance de {@code Component}
     */
    @Override
    public Component copy() {
        return null;
    }

    /**
     * Sérialise l'objet en une chaîne de caractères.
     *
     * @param tabSize Taille de tabulation utilisée dans la sérialisation
     * @return Chaîne de caractères sérialisée
     */
    @Override
    public String serialize(int tabSize){
        return "";
    }
}