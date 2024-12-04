package Component;

import engine.Component;
import engine.GameObject;
import util.Vector2;

enum BoundsType {
    Box,
    Triangle
}

/**
 * Classe abstraite représentant les limites d'un composant.
 * Cette classe doit être étendue pour définir des types spécifiques de limites.
 */
public abstract class Bounds extends Component {
    /**
     * Type de limite.
     */
    public BoundsType type;

    /**
     * Obtient la largeur de la limite.
     * @return la largeur de la limite.
     */
    abstract public float getWidth();

    /**
     * Obtient la hauteur de la limite.
     * @return la hauteur de la limite.
     */
    abstract public float getHeight();

    /**
     * Vérifie la collision entre deux limites.
     * @param b1 La première limite.
     * @param b2 La deuxième limite.
     * @return true si les limites sont en collision, sinon false.
     */
    public static boolean checkCollision(Bounds b1, Bounds b2){
        if (b1.type == b2.type && b1.type == BoundsType.Box){
            return BoxBounds.checkCollision((BoxBounds)b1, (BoxBounds)b2);
        } 
        return false;
    }

    /**
     * Résout la collision entre une limite et un objet de jeu.
     * @param b La limite.
     * @param plr L'objet de jeu.
     */
    public static void resolveCollision(Bounds b, GameObject plr){
        if (b.type == BoundsType.Box){
            BoxBounds box = (BoxBounds)b;
            box.resolveCollision(plr);
        }
    }
}
