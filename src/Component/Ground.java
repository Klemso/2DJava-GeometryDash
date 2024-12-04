package Component;

import engine.*;
import util.Constants;

import java.awt.Graphics2D;
import java.awt.Color;


/**
 * La classe Ground représente un composant de sol dans le jeu.
 * Elle hérite de la classe Component et est responsable de la mise à jour
 * de la position du sol et de la détection des collisions avec le joueur.
 * 
 * @version 1.0
 */
public class Ground extends Component {

    /**
     * Met à jour la position du sol et gère les collisions avec le joueur.
     * 
     * @param dt Le temps écoulé depuis la dernière mise à jour.
     */
    @Override
    public void update(double dt) {
        if (!Window.getWindow().isInEditor) {
            LevelScene scene = (LevelScene) Window.getWindow().getCurrentScene();
            GameObject player = scene.player;

            if (player.transform.position.y + player.getComponent(BoxBounds.class).height >
                    gameObject.transform.position.y) {
                player.transform.position.y = gameObject.transform.position.y -
                        player.getComponent(BoxBounds.class).height;
                      player.getComponent(Player.class).onGround = true;  
                player.getComponent(RigidBody.class).velocity.y = 0;}
            gameObject.transform.position.x = scene.camera.position.x - 10;
        } else {
            gameObject.transform.position.x = Window.getWindow().getCurrentScene().camera.position.x - 10;
        }

    }
    
    /**
     * Dessine le sol à l'écran.
     * 
     * @param g2 L'objet Graphics2D utilisé pour dessiner le sol.
     */
    @Override
    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fillRect((int)gameObject.transform.position.x - 10, (int)gameObject.transform.position.y, Constants.WIDTH + 20, 10);
    }

    /**
     * Crée une copie du composant Ground.
     * 
     * @return Une nouvelle instance de Ground.
     */
    @Override
    public Component copy() {
        return null;
    }

    /**
     * Sérialise le composant Ground en une chaîne de caractères.
     * 
     * @param tabSize La taille de la tabulation utilisée pour la sérialisation.
     * @return Une chaîne de caractères représentant le composant Ground.
     */
    @Override
    public String serialize(int tabSize){
        return "";
    }
}
