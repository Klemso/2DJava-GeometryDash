package Component;

import engine.Component;
import engine.GameObject;
import engine.Window;
import util.Constants;
import util.Vector2;
import engine.ML;
import engine.KL;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * La classe SnapToGrid est un composant qui permet de positionner un objet de jeu sur une grille.
 * Elle ajuste la position de l'objet en fonction de la position de la souris et de la caméra.
 * 
 * @param gridWidth La largeur de la grille.
 * @param gridHeight La hauteur de la grille.
 * 
 * @method update Met à jour la position de l'objet de jeu en fonction de la position de la souris et de la caméra.
 * @param deltaTime Le temps écoulé depuis la dernière mise à jour.
 * 
 * @method draw Dessine l'objet de jeu avec une transparence de 50% à sa position actuelle.
 * @param g2 L'objet Graphics2D utilisé pour dessiner l'objet de jeu.
 * 
 * @method copy Crée une copie du composant SnapToGrid.
 * @return Une copie du composant SnapToGrid.
 * 
 * @method serialize Sérialise le composant SnapToGrid.
 * @param tabSize La taille de la tabulation utilisée pour la sérialisation.
 * @return Une chaîne de caractères représentant le composant sérialisé.
 */

public class SnapToGrid extends Component{

    private float debounceTime = 0.2f;
    private float debounceLeft = 0.0f;

    int gridWidth, gridHeight;

    public SnapToGrid(int gridWidth, int gridHeight){
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    @Override
    public void update(double deltaTime){
        debounceLeft -= deltaTime;
        
        if (this.gameObject.getComponent(Sprite.class) != null) {
            float x = (float)Math.floor((Window.getWindow().mouseListener.x + Window.getWindow().getCurrentScene().camera.position.x + Window.getWindow().mouseListener.dx) / gridWidth);
            float y = (float)Math.floor((Window.getWindow().mouseListener.y + Window.getWindow().getCurrentScene().camera.position.y + Window.getWindow().mouseListener.dy) / gridHeight);
            this.gameObject.transform.position.x = x * gridWidth - Window.getWindow().getCurrentScene().camera.position.x;
            this.gameObject.transform.position.y = y * gridHeight - Window.getWindow().getCurrentScene().camera.position.y;

            if (Window.getWindow().mouseListener.y < Constants.BUTTON_OFFSET_Y &&
                Window.getWindow().mouseListener.mousePressed && Window.getWindow().mouseListener.mouseButton == MouseEvent.BUTTON1 && debounceLeft < 0.0f){
                debounceLeft = debounceTime;
                GameObject object = gameObject.copy();
                object.transform.position = new Vector2(x * gridWidth, y * gridHeight);
                Window.getWindow().getCurrentScene().addGameObject(object);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2){
        Sprite sprite = gameObject.getComponent(Sprite.class);
        if (sprite != null){
            float alpha = 0.5f;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(ac);
            g2.drawImage(sprite.image, (int)gameObject.transform.position.x, (int)gameObject.transform.position.y,
            (int)sprite.width, (int)sprite.height, null);
            alpha = 1.0f;
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(ac);
        }
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public String serialize(int tabSize){
        return "";
    }
    
}
