package engine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Mouse listener qui sera principalement utilisé pour les inputs passés pour le map editor
// (Mouse listener that will be mainly used for inputs passed to the map editor)

public class ML extends MouseAdapter {
    public boolean mousePressed = false; // Indique si un bouton de la souris est pressé
    public boolean mouseDragged = false; // Indique si la souris est déplacée avec un bouton pressé
    public int mouseButton = -1; // -1 si pas de bouton pressé (-1 if no button is pressed)
    public float x = -1.0f, y = -1.0f; // Coordonnées de la souris
    public float dx = -1.0f, dy = -1.0f; // Déplacement de la souris

    @Override
    public void mousePressed(MouseEvent e) {
        this.mousePressed = true; // Un bouton est pressé
        this.mouseButton = e.getButton(); // Check quel bouton est pressé
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) { // Override si un bouton est relâché
        this.mousePressed = false;
        this.mouseDragged = false;
        this.dx = 0; // Réinitialise les déplacements
        this.dy = 0; // Réinitialise les déplacements
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) { // Mets à jour la position si la souris est bougée
        this.x = mouseEvent.getX(); 
        this.y = mouseEvent.getY();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        this.mouseDragged = true;
        this.dx = mouseEvent.getX() - this.x; // Calcule le déplacement horizontal 
        this.dy = mouseEvent.getY() - this.y; // Calcule le déplacement vertical
        this.x = mouseEvent.getX(); // Met à jour la position x
        this.y = mouseEvent.getY(); // Met à jour la position y
    }

}