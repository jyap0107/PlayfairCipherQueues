/**
 * The <code>Phrase</code> simulates a Phrase to be encrypted or decrypted
 * against a Playfair cipher
 *
 * @author Jonathan Yap
 *    e-mail: jonathan.yap@stonybrook.edu
 *    Stony Brook ID: 112946304
 *    CSE214.R04
 **/

import java.util.Queue;
import java.util.LinkedList;
public class Phrase extends LinkedList<Bigram> implements Queue<Bigram> {
    /**
     * Places the given Bigram in the end of the Phrase Queue
     * @param b the Bigram to be added to the Queue
     * <dt>Postcondition</dt>
     *       The Bigram object b has been added to the end of the Phrase
     *          Queue
     */
    public void enqueue(Bigram b) {
        this.addLast(b);
    }
    /**
     * Removes the first element of the Phrase Queue
     * @return the first Bigram in the Phrase Queue
     */
    public Bigram dequeue() {
        return (Bigram) this.remove();
    }

    /**
     * Returns the first element in the Phrase Queue without removing it
     * @return the first Bigram in the phrase Queue
     */
    public Bigram peek() {
        return (Bigram) this.getFirst();
    }

    /**
     * Builds and returns a new Phrase object, which is a queue containing
     * bigrams representing s. The String is first transformed to fit the
     * Playfair Cipher.
     * @param s the String to be transformed into a Phrase object
     * @return A Phrase made from the given String.
     */
    public static Phrase buildPhraseFromStringforEnc(String s) {
        s = s.toUpperCase();
        String s2 = "";
        Phrase p = new Phrase();
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            if (Character.isLetter(s.charAt(j))) {
                s2 += s.charAt(j);
            }
        }
        while (i < s2.length()) {
            Bigram b;
            if (i == s2.length() - 1) {
                b = new Bigram(s2.charAt(i), 'X');
                i++;
            }
            else if (s2.charAt(i) == s2.charAt(i + 1)) {
                b = new Bigram(s2.charAt(i), 'X');
                i++;
            }
            else {
                b = new Bigram(s2.charAt(i), s2.charAt(i + 1));
                i = i + 2;
            }
            p.enqueue(b);
        }
        return p;
    }

    /**
     * Finds the resulting character of a cipher from a right shift within
     * Playfair encryption, given the key and the character.
     * @param k The key used for encryption
     * @param c The original char used to find the encrypted char
     * @return A new encrypted char
     */
    public static char findEncryptedRight(KeyTable k, char c) {
        int i = k.findRow(c);
        int j = k.findCol(c);
        if (j == k.getKeyTable()[0].length - 1) {
            j = 0;
        }
        else {
            j++;
        }
        return k.getKeyTable()[i][j];
    }

    /**
     * Maps letters of a given bigram to the right based on a corresponding
     * KeyTable
     * @param k The table used for encryption
     * @param b The original bigram
     * <dt>Postcondition</dt>
     *     The letters in the given bigram are shifted right along the keytable
     */
    public static void encryptRightShift(KeyTable k, Bigram b) {
        b.setFirst(findEncryptedRight(k, b.getFirstChar()));
        b.setSecond(findEncryptedRight(k, b.getSecondChar()));
    }

    /**
     * Find the resulting character of a cipher from a down shift within
     * Playfair encryption, given a key and a character
     * @param k The key used for encryption
     * @param c The original char used to find the encrypted char
     * @return A new encrypted char
     */
    public static char findEncryptedBottom(KeyTable k, char c) {
        int i = k.findRow(c);
        if (i == k.getKeyTable().length - 1) {
            i = 0;
        }
        else {
            i++;
        }
        return k.getKeyTable()[i][k.findCol(c)];
    }

    /**
     * Maps letters of a given bigram to the one below based on a corresponding
     * KeyTable
     * @param k The table used for encryption
     * @param b The original bigram
     * <dt>Postcondition</dt>
     *     The letters in the given bigram are shifted down along the keytable
     */
    public static void encryptBottomShift(KeyTable k, Bigram b) {
        b.setFirst(findEncryptedBottom(k, b.getFirstChar()));
        b.setSecond(findEncryptedBottom(k, b.getSecondChar()));
    }

    /**
     * Changes the letters of the Bigram by mapping them to a letter of the
     * same row, but the column of the other letter, within the KeyTable.
     * @param k The table used for encryption
     * @param b The bigram used for encryption
     * <dt>Postcondition</dt>
     *      The letters within the given bigram have been swapped accordingly.
     */
    public static void encryptRectangle(KeyTable k, Bigram b) {
        int i1 = k.findRow(b.getFirstChar());
        int j1 = k.findCol(b.getFirstChar());
        int i2 = k.findRow(b.getSecondChar());
        int j2 = k.findCol(b.getSecondChar());
        int temp = j1;
        j1 = j2;
        j2 = temp;
        b.setFirst(k.getKeyTable()[i1][j1]);
        b.setSecond(k.getKeyTable()[i2][j2]);
    }

    /**
     * <dt>Precondition</dt>
     *      <code>key</code> is not null
     * Encrypts this Phrase, storing the encrypted bigrams in a new Phrase queue
     * object and returning it.
     * @param key The key used for encryption
     * @return The encrypted Phrase
     * @throws IllegalArgumentException
     */
    public Phrase encrypt(KeyTable key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Phrase encrypted = new Phrase();
        while (!this.isEmpty()) {
            Bigram b = this.dequeue();
            if (key.findRow(b.getFirstChar()) ==
              key.findRow(b.getSecondChar())) {
                encryptRightShift(key, b);
            }
            else if (key.findCol(b.getFirstChar()) ==
              key.findCol(b.getSecondChar())) {
                encryptBottomShift(key, b);
            }
            else {
                encryptRectangle(key, b);
            }
            encrypted.enqueue(b);
        }
        return encrypted;
    }

    /**
     * Finds the resulting character of a Playfair decryption by mapping to
     * the left of the given character along the Playfair cipher
     * @param k The key used for decryption
     * @param c The character to be decrypted
     * @return The decrypted character
     */
    public static char findDecryptedLeft(KeyTable k, char c) {
        int i = k.findRow(c);
        int j = k.findCol(c);
        if (j == 0) {
            j = k.getKeyTable()[0].length - 1;
        }
        else {
            j--;
        }
        return k.getKeyTable()[i][j];
    }

    /**
     * Maps the letters of a bigram to the left letter along the
     * corresponding KeyTable
     * @param k The key used for decryption
     * @param b The bigram to be decrypted.
     * <dt>Postcondition</dt>
     *      The letters within the given bigram have been shifted leftward.
     */
    public void decryptLeftShift(KeyTable k, Bigram b) {
        b.setFirst(findDecryptedLeft(k, b.getFirstChar()));
        b.setSecond(findDecryptedLeft(k, b.getSecondChar()));
    }

    /**
     * Finds the resulting character of a Playfair decryption by mapping a
     * character to the one above it within the cipher.
     * @param k The key used for decryption
     * @param c The character to be decrypted
     * @return The decrypted character
     */
    public static char findDecryptedTop(KeyTable k, char c) {
        int i = k.findRow(c);
        if (i == 0) {
            i = k.getKeyTable().length - 1;
        }
        else {
            i--;
        }
        return k.getKeyTable()[i][k.findCol(c)];
    }

    /**
     * Maps the letters of the bigram to the ones above them along the
     * corresponding KeyTable
     * @param k The key used for decryption
     * @param b The bigram to be decrypted
     * <dt>Postcondition</dt>
     *     The letters within the given bigram have been shifted upward
     */
    public static void decryptTopShift(KeyTable k, Bigram b) {
        b.setFirst(findDecryptedTop(k, b.getFirstChar()));
        b.setSecond(findDecryptedTop(k, b.getSecondChar()));
    }

    /**
     *<dt>Precondition</dt>
     *      <code>key</code> is not null
     * Decrypts this Phrase, storing the decrypted bigrams in a new Phrase
     * queue object and returning it.
     * @param key The key used for decryption
     * @return A new decrypted Phrase queue object
     * @throws IllegalArgumentException
     */
    public Phrase decrypt(KeyTable key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Phrase decrypted = new Phrase();
        while (!this.isEmpty()) {
            Bigram b = this.dequeue();
            if (key.findRow(b.getFirstChar()) != key.findRow(b.getSecondChar())
            && key.findCol(b.getFirstChar()) != key.findCol(b.getSecondChar())){
                encryptRectangle(key, b);
            }
            else if (key.findCol(b.getFirstChar()) ==
              key.findCol(b.getSecondChar())){
                decryptTopShift(key, b);
            }
            else {
                decryptLeftShift(key, b);
            }
            decrypted.enqueue(b);
        }
        return decrypted;
    }

    /**
     * Return the String representation of a Phrase formed by the collection
     * of Bigrams
     * @return The String representation of a Phrease
     */
    public String toString() {
        String str = "";
        Phrase temp = new Phrase();
        while (!this.isEmpty()) {
            str += ("" + this.peek().getFirstChar() +
              this.peek().getSecondChar());
            temp.enqueue(this.dequeue());
        }
        while (!temp.isEmpty()) {
            this.enqueue(temp.dequeue());
        }
        return str;
    }

}
