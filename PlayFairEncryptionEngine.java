/**
 * A program that takes in user input to create a key for a Playfair cipher,
 * and encrypt and decrypt text accordingly.
 */
/**
 * The <code>PlayFairEncryptionEngine</code> simulates an Engine that
 * creates a Playfair key, and encrypts and decrypts text.
 *
 * @author Jonathan Yap
 *    e-mail: jonathan.yap@stonybrook.edu
 *    Stony Brook ID: 112946304
 *    CSE214.R04
 **/

import java.util.Scanner;

public class PlayFairEncryptionEngine {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean done1 = false;
        boolean done2 = false;
        KeyTable table = null;
        Phrase phraseUsed = null;
        String phrase = null;
        while (!done1) {
            System.out.print("Enter a key phrase: ");
            String input = in.nextLine();
            if (!input.isEmpty()) {
                phrase = input;
            }
            try {
                table = KeyTable.buildFromString(phrase);
                done1 = true;
                System.out.println("Generation Success!");
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument");
            }
        }
        while (!done2) {
            System.out.print("Menu: \n (CK) - Change Key \n (PK) - Print " +
                    "Key \n (EN) - Encrypt \n (DE) - Decrypt \n (Q) Quit \n " +
                    "Please select an option: ");
            String next = in.nextLine().toUpperCase();
            if (next.equals("CK")) {
                System.out.print("Enter a key phrase: ");
                try {
                    String phrase2 = in.nextLine();
                    table = KeyTable.buildFromString(phrase2);
                    System.out.println("Generation Success!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Illegal argument.");
                    continue;
                }
            }
            else if (next.equals("PK")) {
                System.out.println(table.toString());
            }
            else if (next.equals("EN")) {
                System.out.println("Please enter a phrase to encrypt: ");
                String encrypt =
                  in.nextLine().replaceAll("\\s", "");
                phraseUsed = Phrase.buildPhraseFromStringforEnc(encrypt);
                phraseUsed = phraseUsed.encrypt(table);
                System.out.println("Encrypted text is: " +
                  phraseUsed.toString());
            }
            else if (next.equals("DE")) {
                System.out.print("Please enter a phrase to decrypt: ");
                String decrypt = in.nextLine();
                decrypt = decrypt.toUpperCase();
                if (decrypt.contains(" ") || decrypt.contains("J")) {
                    System.out.println("Invalid statement to decrypt.");
                    continue;
                }
                for (int i = 0; i < decrypt.length(); i++) {
                    if (!Character.isLetter(decrypt.charAt(i))) {
                        System.out.println("Invalid statement to decrypt.");
                        continue;
                    }
                }

                Phrase decryptPhrase =
                        Phrase.buildPhraseFromStringforEnc(decrypt);
                decryptPhrase = decryptPhrase.decrypt(table);
                System.out.println("Decrypted text is: " +
                  decryptPhrase.toString());
            }
            else if (next.equals("Q")) {
                done2 = true;
            }
            else {
                System.out.println("Invalid input.");
            }
        }
        in.close();

    }
}
