package Component;

import engine.Window;
import engine.Component;
import java.awt.event.MouseEvent;

/**
 * La classe CameraControls permet de contrôler la caméra en fonction des mouvements de la souris.
 * Elle étend la classe Component.
 */
public class CameraControls extends Component {

    private float prevMx, prevMy;

    /**
     * Constructeur de la classe CameraControls.
     * Initialise les positions précédentes de la souris à 0.0f.
     */
    public CameraControls() {
        prevMx = 0.0f;
        prevMy = 0.0f;
    }

    /**
     * Met à jour la position de la caméra en fonction des mouvements de la souris.
     * @param dt Le temps écoulé depuis la dernière mise à jour.
     */
    @Override
    public void update(double dt) {
        if (Window.getWindow().mouseListener.mousePressed &&
                Window.getWindow().mouseListener.mouseButton == MouseEvent.BUTTON2) {
            float dx = (Window.getWindow().mouseListener.x + Window.getWindow().mouseListener.dx - prevMx);
            float dy = (Window.getWindow().mouseListener.y + Window.getWindow().mouseListener.dy - prevMy);

            Window.getWindow().getCurrentScene().camera.position.x -= dx;
            Window.getWindow().getCurrentScene().camera.position.y -= dy;
        }

        prevMx = Window.getWindow().mouseListener.x + Window.getWindow().mouseListener.dx;
        prevMy = Window.getWindow().mouseListener.y + Window.getWindow().mouseListener.dy;
    }

    /**
     * Crée une copie de ce composant.
     * @return Une copie de ce composant.
     */
    @Override
    public Component copy() {
        return null;
    }

    /**
     * Sérialise ce composant en une chaîne de caractères.
     * @param tabSize La taille de la tabulation.
     * @return Une chaîne de caractères représentant ce composant.
     */
    @Override
    public String serialize(int tabSize){
        return "";
    }
}