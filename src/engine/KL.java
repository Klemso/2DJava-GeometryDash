package engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

/**
 * La classe KL étend KeyAdapter et implémente KeyListener pour gérer les événements de clavier.
 * Elle maintient un tableau d'états des touches pour savoir si une touche est enfoncée ou relâchée.
 * 
 * @author Clement Lores
 */
public class KL extends KeyAdapter implements KeyListener {
    private boolean keyPressed [] = new boolean[128];

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        keyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
    }

    public boolean isKeyPressed(int keyCode){
        return keyPressed[keyCode];
    }
    
}