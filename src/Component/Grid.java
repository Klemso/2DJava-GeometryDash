package Component;

import engine.Window;
import engine.Camera;
import util.Constants;
import engine.Component;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * La classe Grid représente une grille dessinée sur l'écran.
 * Elle hérite de la classe Component.
 * 
 * <p>Cette classe utilise une caméra pour déterminer la position de la grille
 * et dessine des lignes verticales et horizontales pour former la grille.</p>
 * 
 * <p>Les dimensions de la grille sont définies par les constantes TILE_WIDTH et TILE_HEIGHT.</p>
 * 
 * @author Clement Lores
 */
public class Grid extends Component {

    // La caméra utilisée pour déterminer la position de la grille
    Camera camera;
    
    // Largeur et hauteur de la grille
    public int gridWidth, gridHeight;
    
    // Nombre de lignes verticales et horizontales
    private int numYLines = 31;
    private int numXLines = 20;

    /**
     * Constructeur de la classe Grid.
     * Initialise la caméra et les dimensions de la grille.
     */
    public Grid(){
        this.camera = Window.getWindow().getCurrentScene().camera;
        this.gridWidth = Constants.TILE_WIDTH;
        this.gridHeight = Constants.TILE_HEIGHT;
    }

    /**
     * Met à jour l'état de la grille.
     * 
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(double deltaTime){

    }

    /**
     * Dessine la grille sur l'écran.
     * 
     * @param g2 L'objet Graphics2D utilisé pour dessiner la grille
     */
    @Override
    public void draw(Graphics2D g2){
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(0.2f, 0.2f, 0.2f, 0.5f));
        float bottom = Math.min(Constants.GROUND_Y - camera.position.y, Constants.HEIGHT);
        float startX = (float)Math.floor(camera.position.x / gridWidth) * gridWidth - camera.position.x;
        float startY = (float)Math.floor(camera.position.y / gridHeight) * gridHeight - camera.position.y;

        for (int column = 0; column <= numYLines; column++){
            g2.draw(new Line2D.Float(startX, 0, startX, bottom));
            startX += gridWidth;
        }

        for (int row = 0; row <= numXLines; row++){
            if (camera.position.y + startY < Constants.GROUND_Y){
                g2.draw(new Line2D.Float(0, startY, Constants.WIDTH, startY));
                startY += gridHeight;
            }
        }
    }    

    /**
     * Crée une copie de la grille.
     * 
     * @return Une nouvelle instance de Grid (actuellement retourne null)
     */
    @Override  
    public Component copy(){
        return null;
    }

    /**
     * Sérialise la grille en une chaîne de caractères.
     * 
     * @param tabSize La taille de la tabulation utilisée pour la sérialisation
     * @return Une chaîne de caractères représentant la grille (actuellement retourne une chaîne vide)
     */
    @Override
    public String serialize(int tabSize){
        return "";
    }
}
