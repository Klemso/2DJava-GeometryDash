package file;

import Component.BoxBounds;
import Component.Sprite;
import engine.Component;
import engine.GameObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * La classe Parser permet de charger, analyser et interpréter des fichiers de niveau compressés
 * au format ZIP, en extrayant les objets de jeu et leurs composants à partir de fichiers JSON.
 */
public class Parser {
    /** Décalage actuel dans les données lues. */
    private static int offset = 0;

    /** Numéro de ligne actuel dans le fichier. */
    private static int line = 1;

    /** Tableau d'octets contenant les données lues à partir du fichier ZIP. */
    private static byte[] bytes;

    /**
     * Ouvre et charge un fichier ZIP contenant un fichier JSON de niveau.
     *
     * @param filename Le nom du fichier ZIP à ouvrir (sans extension).
     */
    public static void openFile(String filename) {
        File tmp = new File("levels/" + filename + ".zip");
        Parser.bytes = new byte[0];
        Parser.offset = 0;
        Parser.line = 1;
        if (!tmp.exists()) return;

        try {
            ZipFile zipFile = new ZipFile("levels/" + filename + ".zip");
            ZipEntry jsonFile = zipFile.getEntry(filename + ".json");
            InputStream stream = zipFile.getInputStream(jsonFile);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            Parser.bytes = buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Analyse et désérialise un objet de jeu à partir des données lues.
     *
     * @return Un objet GameObject désérialisé, ou null si la fin des données est atteinte.
     */
    public static GameObject parseGameObject() {
        if (bytes.length == 0 || atEnd()) return null;

        if (peek() == ',') Parser.consume(',');
        skipWhitespace();
        if (atEnd()) return null;

        return GameObject.deserialize();
    }

    /**
     * Ignore les espaces blancs et les retours à la ligne dans les données lues.
     */
    public static void skipWhitespace() {
        while (!atEnd() && (peek() == ' ' || peek() == '\n' || peek() == '\t' || peek() == '\r')) {
            if (peek() == '\n') Parser.line++;
            advance();
        }
    }

    /**
     * Retourne le caractère actuel dans les données sans avancer le décalage.
     *
     * @return Le caractère actuel.
     */
    public static char peek() {
        return (char)bytes[offset];
    }

    /**
     * Avance d'un caractère dans les données et le retourne.
     *
     * @return Le caractère avancé.
     */
    public static char advance() {
        char c = (char)bytes[offset];
        offset++;
        return c;
    }

    /**
     * Consomme un caractère spécifique dans les données.
     *
     * @param c Le caractère attendu.
     */
    public static void consume(char c) {
        char actual = peek();
        if (actual != c) {
            System.out.println("Error: Expected '" + c + "' but instead got '" + actual + "' at line: " + Parser.line);
            System.exit(-1);
        }
        offset++;
    }

    /**
     * Vérifie si la fin des données a été atteinte.
     *
     * @return True si la fin est atteinte, sinon false.
     */
    public static boolean atEnd() {
        return offset == bytes.length;
    }

    /**
     * Analyse et retourne un entier à partir des données lues.
     *
     * @return L'entier analysé.
     */
    public static int parseInt() {
        skipWhitespace();
        char c;
        StringBuilder builder = new StringBuilder();

        while(!atEnd() && isDigit(peek()) || peek() == '-') {
            c = advance();
            builder.append(c);
        }

        return Integer.parseInt(builder.toString());
    }

    /**
     * Analyse et retourne un nombre à virgule flottante double précision à partir des données lues.
     *
     * @return Le nombre double analysé.
     */
    public static double parseDouble() {
        skipWhitespace();
        char c;
        StringBuilder builder = new StringBuilder();

        while(!atEnd() && isDigit(peek()) || peek() == '-' || peek() == '.') {
            c = advance();
            builder.append(c);
        }

        return Double.parseDouble(builder.toString());
    }

    /**
     * Analyse et retourne un nombre à virgule flottante simple précision à partir des données lues.
     *
     * @return Le nombre float analysé.
     */
    public static float parseFloat() {
        float f = (float)parseDouble();
        consume('f');
        return f;
    }

    /**
     * Analyse et retourne une chaîne de caractères à partir des données lues.
     *
     * @return La chaîne analysée.
     */
    public static String parseString() {
        skipWhitespace();
        char c;
        StringBuilder builder = new StringBuilder();
        consume('"');

        while (!atEnd() && peek() != '"') {
            c = advance();
            builder.append(c);
        }
        consume('"');

        return builder.toString();
    }

    /**
     * Analyse et retourne une valeur booléenne à partir des données lues.
     *
     * @return La valeur booléenne analysée.
     */
    public static boolean parseBoolean() {
        skipWhitespace();
        StringBuilder builder = new StringBuilder();

        if (!atEnd() && peek() == 't') {
            builder.append("true");
            consume('t');
            consume('r');
            consume('u');
            consume('e');
        } else if (!atEnd() && peek() == 'f') {
            builder.append("false");
            consume('f');
            consume('a');
            consume('l');
            consume('s');
            consume('e');
        } else {
            System.out.println("Expecting 'true' or 'false' instead got: " + peek() + " at line: " + Parser.line);
            System.exit(-1);
        }

        return builder.toString().compareTo("true") == 0;
    }

    /**
     * Vérifie si un caractère est un chiffre.
     *
     * @param c Le caractère à vérifier.
     * @return True si c'est un chiffre, sinon false.
     */
    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * Analyse et retourne un composant à partir des données lues.
     *
     * @return Le composant analysé.
     */
    public static Component parseComponent() {
        String componentTitle = Parser.parseString();
        skipWhitespace();
        Parser.consume(':');
        skipWhitespace();
        Parser.consume('{');

        switch (componentTitle) {
            case "Sprite":
                return Sprite.deserialize();
            case "BoxBounds":
                return BoxBounds.deserialize();
            default:
                System.out.println("Could not find component '" + componentTitle + "' at line: " + Parser.line);
                System.exit(-1);
        }

        return null;
    }

    /**
     * Consomme une propriété de type chaîne de caractères à partir des données lues.
     *
     * @param name Le nom de la propriété.
     * @return La valeur de la propriété.
     */
    public static String consumeStringProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseString();
    }

    /**
     * Consomme une propriété de type entier à partir des données lues.
     *
     * @param name Le nom de la propriété.
     * @return La valeur de la propriété.
     */
    public static int consumeIntProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseInt();
    }

    /**
     * Consomme une propriété de type nombre à virgule flottante simple précision à partir des données lues.
     *
     * @param name Le nom de la propriété.
     * @return La valeur de la propriété.
     */
    public static float consumeFloatProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseFloat();
    }

    /**
     * Consomme une propriété de type nombre à virgule flottante double précision à partir des données lues.
     *
     * @param name Le nom de la propriété.
     * @return La valeur de la propriété.
     */
    public static double consumeDoubleProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseDouble();
    }

    /**
     * Consomme une propriété de type booléen à partir des données lues.
     *
     * @param name Le nom de la propriété.
     * @return La valeur de la propriété.
     */
    public static boolean consumeBooleanProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseBoolean();
    }

    /**
     * Consomme la fin d'un objet dans les données lues.
     */
    public static void consumeEndObjectProperty() {
        skipWhitespace();
        consume('}');
    }

    /**
     * Consomme le début d'un objet dans les données lues, après avoir vérifié un nom de propriété.
     *
     * @param name Le nom de la propriété.
     */
    public static void consumeBeginObjectProperty(String name) {
        skipWhitespace();
        checkString(name);
        skipWhitespace();
        consume(':');
        skipWhitespace();
        consume('{');
    }

    /**
     * Vérifie que la chaîne de caractères lue correspond à la chaîne attendue.
     *
     * @param str La chaîne attendue.
     */
    private static void checkString(String str) {
        String title = Parser.parseString();
        if (title.compareTo(str) != 0) {
            System.out.println("Expected '" + str + "' instead got '" + title + "' at line: " + Parser.line);
            System.exit(-1);
        }
    }
}
