package dataStructure;

import util.Vector2;
import file.Parser;
import file.Serialize;


/**
 * La classe Transform représente une transformation dans un espace 2D, 
 * incluant la position, l'échelle et la rotation.
 * Elle hérite de la classe Serialize pour permettre la sérialisation et la désérialisation.
 */
public class Transform extends Serialize {

    /**
     * La position de la transformation sous forme de vecteur 2D.
     */
    public Vector2 position;

    /**
     * L'échelle de la transformation sous forme de vecteur 2D.
     */
    public Vector2 scale;

    /**
     * La rotation de la transformation en degrés.
     */
    public float rotation;

    /**
     * Constructeur qui initialise la transformation avec une position donnée.
     * L'échelle est initialisée à (1.0, 1.0) et la rotation à 0.0.
     *
     * @param position La position initiale de la transformation.
     */
    public Transform(Vector2 position) {
        this.position = position;
        this.scale = new Vector2(1.0f, 1.0f);
        this.rotation = 0.0f;
    }

    /**
     * Crée une copie de cette transformation.
     *
     * @return Une nouvelle instance de Transform avec les mêmes valeurs de position, échelle et rotation.
     */
    public Transform copy() {
        Transform transform = new Transform(this.position.copy());
        transform.scale = this.scale.copy();
        transform.rotation = this.rotation;
        return transform;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de cette transformation.
     *
     * @return Une chaîne de caractères représentant la position de cette transformation.
     */
    @Override
    public String toString() {
        return "position=" + position.x + ", " + position.y;
    }

    /**
     * Sérialise cette transformation en une chaîne de caractères formatée.
     *
     * @param tabSize Le nombre d'espaces à utiliser pour l'indentation.
     * @return Une chaîne de caractères représentant cette transformation en format sérialisé.
     */
    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("Transform", tabSize));

        builder.append(beginObjectProperty("Position", tabSize + 1));
        builder.append(position.serialize(tabSize + 2));
        builder.append(closeObjectProperty(tabSize + 1));
        builder.append(addEnding(true, true));
        
        builder.append(beginObjectProperty("Scale", tabSize + 1));
        builder.append(scale.serialize(tabSize + 2));
        builder.append(closeObjectProperty(tabSize + 1));
        builder.append(addEnding(true, true));

        builder.append(addFloatProperty("rotation", rotation, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    /**
     * Désérialise une chaîne de caractères en une instance de Transform.
     *
     * @return Une nouvelle instance de Transform désérialisée.
     */
    public static Transform deserialize() {
        Parser.consumeBeginObjectProperty("Transform");
        Parser.consumeBeginObjectProperty("Position");
        Vector2 position = Vector2.deserialize();
        Parser.consumeEndObjectProperty();

        Parser.consume(',');
        Parser.consumeBeginObjectProperty("Scale");
        Vector2 scale = Vector2.deserialize();
        Parser.consumeEndObjectProperty();

        Parser.consume(',');
        float rotation = Parser.consumeFloatProperty("rotation");
        Parser.consumeEndObjectProperty();

        Transform t = new Transform(position);
        t.scale = scale;
        t.rotation = rotation;

        return t;
    }
}

