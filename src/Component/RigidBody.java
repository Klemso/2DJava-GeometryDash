package Component;

import util.Constants;
import util.Vector2;
import engine.GameObject;
import dataStructure.Transform;
import engine.Component;


/**
 * La classe RigidBody représente un composant de corps rigide dans un jeu.
 * Elle hérite de la classe Component et gère la physique de base comme la vélocité et la gravité.
 * 
 * @author Clement
 */
public class RigidBody extends Component {
    
    /**
     * La vélocité du corps rigide représentée par un vecteur 2D.
     */
    public Vector2 velocity;

    /**
     * Constructeur de la classe RigidBody.
     * 
     * @param vel La vélocité initiale du corps rigide.
     */
    public RigidBody(Vector2 vel){
        this.velocity = vel;
    }

    /**
     * Met à jour la position et la vélocité du corps rigide en fonction du temps écoulé.
     * 
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour.
     */
    @Override
    public void update(double deltaTime){
        // Mise à jour de la position en fonction de la vélocité et du temps écoulé
        gameObject.transform.position.y += velocity.y * deltaTime;
        gameObject.transform.position.x += velocity.x * deltaTime;

        // Application de la gravité à la vélocité
        velocity.y += Constants.GRAVITY * deltaTime;

        // Limitation de la vélocité maximale
        if (Math.abs(velocity.y) > Constants.MAX_VELOCITY) {
            velocity.y = Math.signum(velocity.y) * Constants.MAX_VELOCITY;
        }  
    }

    /**
     * Crée une copie du composant RigidBody.
     * 
     * @return Une nouvelle instance de RigidBody (actuellement retourne null).
     */
    @Override
    public Component copy() {
        return null;
    }

    /**
     * Sérialise le composant RigidBody en une chaîne de caractères.
     * 
     * @param tabSize La taille de la tabulation pour la sérialisation.
     * @return Une chaîne de caractères représentant le composant (actuellement retourne une chaîne vide).
     */
    @Override
    public String serialize(int tabSize){
        return "";
    }
    
}
