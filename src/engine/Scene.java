package engine;

import java.awt.Graphics2D;
import java.util.List;

import engine.Camera;
import engine.GameObject;
import engine.Renderer;
import engine.Scene;

import util.Vector2;
import java.util.ArrayList;
import engine.GameObject;


/**
 * La classe abstraite Scene représente une scène dans le moteur de jeu.
 * Elle contient des informations sur la caméra, les objets de jeu, et le renderer.
 * 
 * @author Clement Lores
 */
public abstract class Scene {
    /**
     * Le nom de la scène.
     */
    String name;

    /**
     * La caméra utilisée dans la scène.
     */
    public Camera camera;

    /**
     * La liste des objets de jeu présents dans la scène.
     */
    List<GameObject> gameObjects;

    /**
     * Le renderer utilisé pour dessiner la scène.
     */
    Renderer renderer;

    /**
     * Constructeur de la classe Scene.
     * 
     * @param name Le nom de la scène.
     */
    public void Scene(String name){
        this.name = name;
        this.camera = new Camera(new Vector2());
        this.gameObjects = new ArrayList<>();
        this.renderer = new Renderer(this.camera);
    }

    /**
     * Méthode d'initialisation de la scène.
     */
    public void init(){
    }
    
    /**
     * Ajoute un objet de jeu à la scène.
     * 
     * @param g L'objet de jeu à ajouter.
     */
    public void addGameObject(GameObject g){
        gameObjects.add(g);
        renderer.submit(g);
        for (Component c : g.getAllComponents()){
            c.start();
        }
    }

    /**
     * Met à jour la scène.
     * 
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour.
     */
    public abstract void update(double deltaTime);

    /**
     * Dessine la scène.
     * 
     * @param g2 L'objet Graphics2D utilisé pour dessiner.
     */
    public abstract void draw(Graphics2D g2);
}
