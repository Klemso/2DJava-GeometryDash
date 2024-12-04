package file;

/**
 * La classe abstraite Serialize fournit des méthodes utilitaires pour sérialiser des objets et leurs propriétés
 * sous forme de chaînes JSON, en permettant de définir les propriétés des objets avec un formatage flexible.
 */
public abstract class Serialize {

    /**
     * Sérialise l'objet en une chaîne de caractères avec un format spécifique.
     *
     * @param tabSize Le nombre de tabulations à ajouter avant chaque ligne.
     * @return La chaîne sérialisée de l'objet.
     */
    public abstract String serialize(int tabSize);

    /**
     * Ajoute une propriété de type chaîne à la sérialisation.
     *
     * @param name Le nom de la propriété.
     * @param value La valeur de la propriété.
     * @param tabSize Le nombre de tabulations à ajouter avant la propriété.
     * @param newline Si true, ajoute une nouvelle ligne après la propriété.
     * @param comma Si true, ajoute une virgule après la propriété.
     * @return La chaîne de la propriété formatée.
     */
    public String addStringProperty(String name, String value, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + "\"" + value + "\"" + addEnding(newline, comma);
    }

    /**
     * Ajoute une propriété de type entier à la sérialisation.
     *
     * @param name Le nom de la propriété.
     * @param value La valeur de la propriété.
     * @param tabSize Le nombre de tabulations à ajouter avant la propriété.
     * @param newline Si true, ajoute une nouvelle ligne après la propriété.
     * @param comma Si true, ajoute une virgule après la propriété.
     * @return La chaîne de la propriété formatée.
     */
    public String addIntProperty(String name, int value, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + value + addEnding(newline, comma);
    }

    /**
     * Ajoute une propriété de type nombre flottant simple précision à la sérialisation.
     *
     * @param name Le nom de la propriété.
     * @param value La valeur de la propriété.
     * @param tabSize Le nombre de tabulations à ajouter avant la propriété.
     * @param newline Si true, ajoute une nouvelle ligne après la propriété.
     * @param comma Si true, ajoute une virgule après la propriété.
     * @return La chaîne de la propriété formatée.
     */
    public String addFloatProperty(String name, float value, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + value + "f" + addEnding(newline, comma);
    }

    /**
     * Ajoute une propriété de type nombre flottant double précision à la sérialisation.
     *
     * @param name Le nom de la propriété.
     * @param value La valeur de la propriété.
     * @param tabSize Le nombre de tabulations à ajouter avant la propriété.
     * @param newline Si true, ajoute une nouvelle ligne après la propriété.
     * @param comma Si true, ajoute une virgule après la propriété.
     * @return La chaîne de la propriété formatée.
     */
    public String addDoubleProperty(String name, double value, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + value + addEnding(newline, comma);
    }

    /**
     * Ajoute une propriété de type booléen à la sérialisation.
     *
     * @param name Le nom de la propriété.
     * @param val La valeur de la propriété.
     * @param tabSize Le nombre de tabulations à ajouter avant la propriété.
     * @param newline Si true, ajoute une nouvelle ligne après la propriété.
     * @param comma Si true, ajoute une virgule après la propriété.
     * @return La chaîne de la propriété formatée.
     */
    public String addBooleanProperty(String name, boolean val, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + val + addEnding(newline, comma);
    }

    /**
     * Débute un objet dans la sérialisation.
     *
     * @param name Le nom de la propriété représentant l'objet.
     * @param tabSize Le nombre de tabulations à ajouter avant l'objet.
     * @return La chaîne débutant l'objet sérialisé.
     */
    public String beginObjectProperty(String name, int tabSize) {
        return addTabs(tabSize) + "\"" + name + "\": {" + addEnding(true, false);
    }

    /**
     * Ferme un objet dans la sérialisation.
     *
     * @param tabSize Le nombre de tabulations à ajouter avant la fermeture de l'objet.
     * @return La chaîne fermant l'objet sérialisé.
     */
    public String closeObjectProperty(int tabSize) {
        return addTabs(tabSize) + "}";
    }

    /**
     * Ajoute un certain nombre de tabulations (espaces d'indentation) dans la chaîne.
     *
     * @param tabSize Le nombre de tabulations à ajouter.
     * @return La chaîne d'indentation.
     */
    public String addTabs(int tabSize) {
        StringBuilder str = new StringBuilder();
        for (int i=0; i < tabSize; i++) {
            str.append("\t");
        }
        return str.toString();
    }

    /**
     * Ajoute une virgule ou un retour à la ligne à la fin d'une propriété selon les paramètres.
     *
     * @param newline Si true, ajoute une nouvelle ligne.
     * @param comma Si true, ajoute une virgule.
     * @return La chaîne d'éléments de fin.
     */
    public String addEnding(boolean newline, boolean comma) {
        String str = "";
        if (comma) str += ",";
        if (newline) str += "\n";
        return str;
    }
}
