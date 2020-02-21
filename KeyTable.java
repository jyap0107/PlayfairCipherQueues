import java.sql.SQLOutput;
/**
 * The <code>KeyTable</code> simulates a Playfair cipher
 *
 * @author Jonathan Yap
 *    e-mail: jonathan.yap@stonybrook.edu
 *    Stony Brook ID: 112946304
 *    CSE214.R04
 **/
public class KeyTable {
    private static final int MAX_ROWS = 5;
    private static final int MAX_COLUMNS = 5;
    private char[][] key = new char[MAX_ROWS][MAX_COLUMNS];

    /**
     * Constructs a KeyTable object with an uninitialized key.
     *<dt>Postcondition</dt>
     *      A KeyTable object has been created with an uninitialized key.
     */
    public KeyTable() {
        char[][] key;
    }

    /**
     * Builds a new KeyTable object from the provided string and returns it
     * <dt>Precondition</dt>
     *      KeyPhrase is not null
     * @param keyphrase the String to be turned into the key.
     * @return a new Keytable Object with an initialized key.
     * @throws IllegalArgumentException
     */
    public static KeyTable buildFromString(String keyphrase) throws
            IllegalArgumentException {
        String newString = "";
        if (keyphrase == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < keyphrase.length(); i++) {
            if (!newString.contains(("" + keyphrase.charAt(i))) &&
                  Character.isLetter(keyphrase.charAt(i))) {
                newString += keyphrase.charAt(i);
            }
        }
        newString = newString.replaceAll("\\s", "");
        newString = newString.toUpperCase();
        String newString2 = "";
        for (int i = 0; i < newString.length(); i++) {
            if (newString.charAt(i) != 'J') {
                newString2 += newString.charAt(i) + "";
            }
        }
        for (int i = 1; i <= 26; i++) {
            if (i == 10) {
                continue;
            }
            // +64 gives ASCII equivalent.
            else {
                if ((!newString2.contains("" + ((char) (i + 64)))) && i != 10) {
                    newString2 += ((char) (i + 64));
                }
            }
        }
        int indexOfNewString = 0;
        KeyTable newTable = new KeyTable();
        for (int i = 0; i < newTable.key.length; i++) {
            for (int j = 0; j < newTable.key[0].length; j++) {
                newTable.key[i][j] = newString2.charAt(indexOfNewString);
                indexOfNewString++;
            }
        }
        return newTable;
    }

    /**
     * Returns the key matrix.
     * @return the key matrix of the KeyTable object.
     */
    public char[][] getKeyTable() {
        return key;
    }

    /**
     * <dt>Precondition</dt>
     *      c is a valid letter.
     * Returns the row in which the char c occurs.
     * @param c the character that is searched for
     * @return the row in which the character c is found
     * @throws IllegalArgumentException
     */
    public int findRow(char c) throws IllegalArgumentException {
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key[0].length; j++) {
                if (this.key[i][j] == c) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException();
    }
    /**
     * <dt>Precondition</dt>
     *      c is a valid letter.
     * Returns the column in which the char c occurs.
     * @param c the character that is searched for
     * @return the column in which the character c is found
     * @throws IllegalArgumentException
     */
    public int findCol(char c) {
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key[0].length; j++) {
                if (this.key[i][j] == c) {
                    return j;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Converts the key matrix into a String
     * @return the key matrix represented as a String
     */
    public String toString() {
        String returnString = "";
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key[0].length; j++) {
                returnString += "" + this.key[i][j];
                if (j != key[0].length - 1) {
                    returnString += " ";
                }
            }
            returnString += "\n";
        }
        return returnString;
    }

}
