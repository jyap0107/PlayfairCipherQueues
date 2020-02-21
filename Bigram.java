/**
 * The <code>Bigram/code> simulates a Bigram containing two letters.
 *
 * @author Jonathan Yap
 *    e-mail: jonathan.yap@stonybrook.edu
 *    Stony Brook ID: 112946304
 *    CSE214.R04
 **/
public class Bigram {
    private char first, second;

    /**
     * Constructs an empty Bigram object;
     * <dt>Postcondition</dt>
     *      An empty Bigram object has been created.
     */
    public Bigram() {
    }

    /**
     * Constructs a Bigram object with the given characters
     * @param f the first letter of the bigram.
     * @param s the second letter of the bigram.
     * <dt>Postcondition</dt>
     *      A Bigram object with letters has been created.
     */
    public Bigram(char f, char s) {
        first = f;
        second = s;
    }

    /**
     * Converts the Bigram to a String
     * @return A String representation of the Bigram
     */
    public String toString() {
        return first + second + "";
    }

    /**
     * Returns the first character of the Bigram
     * @return the first character of the Bigram
     */
    public char getFirstChar() {
        return first;
    }

    /**
     * Returns the second character of the Bigram
     * @return the second character of the Bigram
     */
    public char getSecondChar() {
        return second;
    }

    /**
     * Sets the first character of the Bigram to a given character c
     * @param c the letter to be set to the first character of the Bigram
     */
    public void setFirst(char c) {
        first = c;
    }

    /**
     * Sets the second character of the Bigram to a given character c
     * @param c the letter to be set to the second character of the Bigram
     */
    public void setSecond(char c) {
        second = c;
    }
}
