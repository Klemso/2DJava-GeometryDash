package engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import dataStructure.Transform;
import util.Vector2;

/**
 * Classe responsable du rendu des objets de jeu en fonction de leur position et de leur profondeur (zIndex).
 */
public class Renderer {

    /** Carte contenant les objets de jeu, regroupés par leur zIndex. */
    private Map<Integer, List<GameObject>> gameObjects;

    /** Caméra utilisée pour ajuster le rendu en fonction de la position du joueur ou de la scène. */
    private Camera camera;

    /**
     * Constructeur du renderer.
     *
     * @param camera Caméra utilisée pour déterminer le décalage lors du rendu.
     */
    public Renderer(Camera camera) {
        this.camera = camera;
        this.gameObjects = new HashMap<>();
    }

    /**
     * Ajoute un objet de jeu à rendre dans le renderer.
     *
     * @param gameObject Objet de jeu à ajouter.
     */
    public void submit(GameObject gameObject) {
        // Regroupe les objets de jeu par leur zIndex
        gameObjects.computeIfAbsent(gameObject.zIndex, k -> new ArrayList<>());
        gameObjects.get(gameObject.zIndex).add(gameObject);
    }

    /**
     * Rendu de tous les objets de jeu en fonction de leur profondeur (zIndex).
     *
     * @param g2 Contexte graphique utilisé pour le rendu.
     */
    public void render(Graphics g2) {
        // Trouve les zIndex extrêmes (le plus bas et le plus haut)
        int lowestZIndex = Integer.MAX_VALUE;
        int highestZIndex = Integer.MIN_VALUE;
        for (Integer i : gameObjects.keySet()) {
            if (i < lowestZIndex) lowestZIndex = i;
            if (i > highestZIndex) highestZIndex = i;
        }

        // Rendu des objets par ordre de profondeur
        int currentZIndex = lowestZIndex;
        while (currentZIndex <= highestZIndex) {
            if (gameObjects.get(currentZIndex) == null) {
                currentZIndex++;
                continue;
            }

            Graphics2D g2d = (Graphics2D) g2;
            for (GameObject g : gameObjects.get(currentZIndex)) {
                if (g.isUi) {
                    // Rendu des éléments d'interface utilisateur (UI) sans tenir compte de la caméra
                    g.draw(g2d);
                } else {
                    // Sauvegarde de la transformation originale
                    Transform oldTransform = new Transform(g.transform.position);
                    oldTransform.rotation = g.transform.rotation;
                    oldTransform.scale = new Vector2(g.transform.scale.x, g.transform.scale.y);

                    // Applique le décalage de la caméra pour le rendu
                    g.transform.position = new Vector2(
                        g.transform.position.x - camera.position.x,
                        g.transform.position.y - camera.position.y
                    );

                    // Rendu de l'objet
                    g.draw(g2d);

                    // Restauration de la transformation originale
                    g.transform = oldTransform;
                }
            }
            currentZIndex++;
        }
    }
}
