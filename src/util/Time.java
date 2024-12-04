package util;

/**
 * Cette classe fournit des méthodes pour mesurer le temps écoulé depuis son démarrage en secondes.
 */
public class Time {

    /**
     * Temps de départ du programme, exprimé en nanosecondes.
     */
    public static double timeStarted = System.nanoTime();

    /**
     * Retourne le temps écoulé depuis le démarrage du programme, en secondes.
     * 
     * @return Le temps écoulé en secondes.
     */
    public static double getTime() {
        return (System.nanoTime() - timeStarted) / 1000000000;
    }
}
