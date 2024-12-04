package util;

import file.Parser;
import file.Serialize;

/**
 * Représente un vecteur à deux dimensions avec des coordonnées flottantes (x, y).
 * Fournit des méthodes pour la sérialisation et la désérialisation de l'objet.
 */
public class Vector2 extends Serialize {
    /**
     * Coordonnée X du vecteur.
     */
    public float x, y;

    /**
     * Constructeur de la classe Vector2 avec des coordonnées spécifiées.
     * 
     * @param x La coordonnée X du vecteur.
     * @param y La coordonnée Y du vecteur.
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructeur par défaut. Initialise les coordonnées à (0, 0).
     */
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Crée une copie du vecteur actuel.
     * 
     * @return Une nouvelle instance de Vector2 avec les mêmes coordonnées (x, y).
     */
    public Vector2 copy() {
        return new Vector2(this.x, this.y);
    }

    /**
     * Sérialise les coordonnées du vecteur en une chaîne de caractères.
     * 
     * @param tabSize Le nombre d'indentations à appliquer pour la sérialisation.
     * @return La représentation sous forme de chaîne des coordonnées (x, y) du vecteur.
     */
    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(addFloatProperty("x", x, tabSize, true, true));
        builder.append(addFloatProperty("y", y, tabSize, true, false));

        return builder.toString();
    }

    /**
     * Désérialise une chaîne de caractères pour récupérer les coordonnées d'un vecteur.
     * 
     * @return Un nouvel objet Vector2 avec les coordonnées (x, y) extraites de la chaîne.
     */
    public static Vector2 deserialize() {
        float x = Parser.consumeFloatProperty("x");
        Parser.consume(',');  // Consomme la virgule séparant x et y
        float y = Parser.consumeFloatProperty("y");

        return new Vector2(x, y);
    }
}
