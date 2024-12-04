package engine;

import java.util.ArrayList;
import java.util.List;
import dataStructure.Transform;
import java.awt.Graphics2D;

import file.Parser;
import file.Serialize;

/**
 * Représente un objet dans le jeu, contenant des composants, une position et un ordre d'affichage.
 * Les GameObjects sont les éléments de base dans le moteur de jeu.
 */
public class GameObject extends Serialize {
    
    /** Liste des composants associés au GameObject. */
    private List<Component> components;

    /** Nom du GameObject. */
    private String name;

    /** Transformation représentant la position, la rotation et l'échelle du GameObject. */
    public Transform transform;

    /** Indique si le GameObject peut être sérialisé. */
    private boolean serializable = true;

    /** Ordre d'affichage (plus grand = plus proche de l'utilisateur). */
    public int zIndex;

    /** Indique si le GameObject est un élément d'interface utilisateur (UI). */
    public boolean isUi = false;

    /**
     * Constructeur pour initialiser un GameObject.
     * 
     * @param name Nom du GameObject.
     * @param transform Transformation associée.
     * @param zIndex Ordre d'affichage.
     */
    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        this.transform = transform;
        this.components = new ArrayList<>();
        this.zIndex = zIndex;
    }

    /**
     * Récupère un composant spécifique du GameObject.
     * 
     * @param <T> Type du composant à récupérer.
     * @param componentClass Classe du composant à rechercher.
     * @return Le composant correspondant, ou null s'il n'est pas trouvé.
     */
    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    System.out.println("Error casting component");
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Supprime un composant spécifique du GameObject.
     * 
     * @param <T> Type du composant à supprimer.
     * @param componentClass Classe du composant à supprimer.
     */
    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(c);
                return;
            }
        }
    }

    /**
     * Récupère la liste complète des composants du GameObject.
     * 
     * @return La liste des composants.
     */
    public List<Component> getAllComponents() {
        return this.components;
    }

    /**
     * Ajoute un composant au GameObject.
     * 
     * @param c Le composant à ajouter.
     */
    public void addComponent(Component c) {
        components.add(c);
        c.gameObject = this;
    }

    /**
     * Crée une copie du GameObject avec tous ses composants.
     * 
     * @return Une nouvelle instance du GameObject.
     */
    public GameObject copy() {
        GameObject newGameObject = new GameObject("Generated", transform.copy(), this.zIndex);
        for (Component c : components) {
            Component copy = c.copy();
            if (copy != null) {
                newGameObject.addComponent(copy);
            }
        }
        return newGameObject;
    }

    /**
     * Met à jour tous les composants du GameObject.
     * 
     * @param deltaTime Temps écoulé depuis la dernière mise à jour.
     */
    public void update(double deltaTime) {
        for (Component c : components) {
            c.update(deltaTime);
        }
    }

    /**
     * Définit que le GameObject ne peut pas être sérialisé.
     */
    public void setNonserializable() {
        serializable = false;
    }

    /**
     * Dessine le GameObject et tous ses composants.
     * 
     * @param g2 Contexte graphique utilisé pour le rendu.
     */
    public void draw(Graphics2D g2) {
        for (Component c : components) {
            c.draw(g2);
        }
    }

    /**
     * Sérialise le GameObject en une chaîne de caractères.
     * 
     * @param tabSize Niveau d'indentation pour la sérialisation.
     * @return La chaîne de caractères représentant le GameObject.
     */
    @Override
    public String serialize(int tabSize) {
        if (!serializable) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        // GameObject serialize
        builder.append(beginObjectProperty("GameObject", tabSize));
        
        // Transform
        builder.append(transform.serialize(tabSize + 1));
        builder.append(addEnding(true, true));

        builder.append(addStringProperty("Name", name, tabSize + 1, true, true));

        // Name
        if (components.size() > 0) {
            builder.append(addIntProperty("ZIndex", this.zIndex, tabSize + 1, true, true));
            builder.append(beginObjectProperty("Components", tabSize + 1));
        } else {
            builder.append(addIntProperty("ZIndex", this.zIndex, tabSize + 1, true, false));
        }

        int i = 0;
        for (Component c : components) {
            String str = c.serialize(tabSize + 2);
            if (!str.isEmpty()) {
                builder.append(str);
                if (i < components.size() - 1) {
                    builder.append(addEnding(true, true));
                } else {
                    builder.append(addEnding(true, false));
                }
            }
            i++;
        }

        if (components.size() > 0) {
            builder.append(closeObjectProperty(tabSize + 1));
        }

        builder.append(addEnding(true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    /**
     * Désérialise un GameObject à partir de données sérialisées.
     * 
     * @return Une instance de GameObject recréée à partir des données.
     */
    public static GameObject deserialize() {
        Parser.consumeBeginObjectProperty("GameObject");

        Transform transform = Transform.deserialize();
        Parser.consume(',');

        String name = Parser.consumeStringProperty("Name");
        Parser.consume(',');

        int zIndex = Parser.consumeIntProperty("ZIndex");

        GameObject gameObject = new GameObject(name, transform, zIndex);

        if (Parser.peek() == ',') {
            Parser.consume(',');
            Parser.consumeBeginObjectProperty("Components");
            gameObject.addComponent(Parser.parseComponent());

            while (Parser.peek() == ',') {
                Parser.consume(',');
                gameObject.addComponent(Parser.parseComponent());
            }
            Parser.consumeEndObjectProperty();
        }

        Parser.consumeEndObjectProperty();

        return gameObject;
    }

    /**
     * Définit si le GameObject est un élément d'interface utilisateur (UI).
     * 
     * @param val True pour définir comme UI, false sinon.
     */
    public void setUi(boolean val) {
        this.isUi = val;
    }
}
