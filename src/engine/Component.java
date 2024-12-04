package engine;

import java.awt.Graphics2D;

import file.Serialize;

/**
 * Composant abstrait de base pour tous les composants du jeu.
 * 
 * @param <T> Le type de données que ce composant peut contenir ou manipuler.
 */
public abstract class Component<T> extends Serialize {

    public GameObject gameObject;

    public void update(double deltaTime){
        return;
    }

    public void draw(Graphics2D g2){
        return;
    }

    public void start(){
        return;
    }

    public Component copy(){
        return null;
    }
}