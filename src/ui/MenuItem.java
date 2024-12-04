package ui;

import engine.GameObject;
import engine.LevelEditorScene;
import engine.Component;
import dataStructure.Transform;
import Component.Spritesheet;
import Component.SnapToGrid;
import Component.Sprite;
import util.Vector2;
import util.Constants;
import engine.Window;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Composant représentant un élément de menu dans l'éditeur de niveau.
 * Il gère l'affichage et les interactions de l'élément de menu, comme la sélection et le déplacement.
 */
public class MenuItem extends Component{
    /**
     * Position et dimensions de l'élément de menu.
     */
    private int x, y, width, height;

    /**
     * Sprite de l'élément de menu pour l'état normal et l'état survolé.
     */
    private Sprite buttonSprite, hoverSprite, myImage;

    /**
     * Indicateur de sélection de l'élément de menu.
     */
    public boolean isSelected;

    /**
     * Décalage pour centrer l'image dans l'élément de menu.
     */
    private int bufferX, bufferY;

    /**
     * Constructeur de l'élément de menu.
     * 
     * @param x Position X de l'élément de menu.
     * @param y Position Y de l'élément de menu.
     * @param width Largeur de l'élément de menu.
     * @param height Hauteur de l'élément de menu.
     * @param buttonSprite Sprite à afficher quand l'élément est en état normal.
     * @param hoverSprite Sprite à afficher quand l'élément est survolé.
     */
    public MenuItem(int x, int y, int width, int height, Sprite buttonSprite, Sprite hoverSprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonSprite = buttonSprite;
        this.hoverSprite = hoverSprite;
        this.isSelected = false;
    }

    /**
     * Initialisation du composant MenuItem.
     * Récupère le sprite de l'image et calcule les décalages pour centrer l'image.
     */
    @Override
    public void start() {
        myImage = gameObject.getComponent(Sprite.class);

        this.bufferX = (int)(this.width - myImage.width) / 2;
        this.bufferY = (int)(this.height - myImage.height) / 2;
    }

    /**
     * Mise à jour de l'élément de menu.
     * Gère la détection des clics de souris et la sélection de l'élément.
     * 
     * @param deltaTime Temps écoulé depuis la dernière mise à jour, en secondes.
     */
    @Override
    public void update(double deltaTime) {
        // Vérifie si la souris est sur l'élément de menu et si un clic gauche a été effectué
        if (!isSelected && 
            Window.getWindow().mouseListener.x > this.x && Window.getWindow().mouseListener.x <= this.x + this.width &&
             Window.getWindow().mouseListener.y > this.y && Window.getWindow().mouseListener.y <= this.y + this.height) {
            if (Window.getWindow().mouseListener.mousePressed && 
                Window.getWindow().mouseListener.mouseButton == MouseEvent.BUTTON1) {
                // Crée une copie de l'objet et ajoute un composant SnapToGrid
                GameObject obj = gameObject.copy();
                obj.removeComponent(MenuItem.class);
                LevelEditorScene scene = (LevelEditorScene)Window.getWindow().getCurrentScene();

                SnapToGrid snapToGrid = scene.mouseCursor.getComponent(SnapToGrid.class);
                obj.addComponent(snapToGrid);
                scene.mouseCursor = obj;
                isSelected = true;
            } 
        }
    }

    /**
     * Crée une copie de l'élément de menu.
     * Cette méthode retourne null car la copie n'est pas implémentée.
     * 
     * @return null, la copie n'est pas implémentée.
     */
    @Override
    public Component copy() {
        return null;
    }

    /**
     * Dessine l'élément de menu sur l'écran.
     * Affiche le sprite de base, puis l'image, et enfin le sprite de survol si l'élément est sélectionné.
     * 
     * @param g2 Le contexte graphique utilisé pour dessiner l'élément.
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(this.buttonSprite.image, this.x, this.y, this.width, this.height, null);
        g2.drawImage(myImage.image, this.x + bufferX, this.y + bufferY, myImage.width, myImage.height, null);
        if (isSelected) {
            g2.drawImage(this.hoverSprite.image, this.x, this.y, this.width, this.height, null);
        }
    }

    /**
     * Sérialise l'élément de menu en une chaîne de caractères.
     * Cette méthode retourne une chaîne vide car la sérialisation n'est pas implémentée.
     * 
     * @param tabSize Le nombre d'indentations à utiliser dans la chaîne sérialisée.
     * @return Une chaîne vide, la sérialisation n'est pas implémentée.
     */
    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
