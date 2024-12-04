package engine;

import util.Vector2;

/**
 * Représente une caméra dans le jeu.
 * 
 * <p>Cette classe gère la position de la caméra dans un espace 2D.</p>
 * 
 * @author Clement Lores
 */
public class Camera {

    /**
     * La position de la caméra dans l'espace 2D.
     */
    public Vector2 position;

    /**
     * Crée une nouvelle instance de Camera avec une position spécifiée.
     * 
     * @param position La position initiale de la caméra.
     */
    public Camera(Vector2 position){
        this.position = position;
    }

}
