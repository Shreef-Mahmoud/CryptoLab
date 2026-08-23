package Security;
import java.util.*;

public class CaeserCipher {
    public String encrypt(String plainText, int key) {
        plainText = plainText.toLowerCase();
        char[] charArr = plainText.toCharArray();

        for (int i = 0; i < charArr.length; i++) {
            // Formula: E(x) = (x + k) mod 26
            charArr[i] = (char) ('a' + (charArr[i] - 'a' + key) % 26);
        }
        return new String(charArr);
    }

    public String decrypt(String cipherText, int key) {
        cipherText = cipherText.toLowerCase();
        char[] charArr = cipherText.toCharArray();

        for (int i = 0; i < charArr.length; i++) {
            // Formula: D(x) = (x - k + 26) mod 26
            charArr[i] = (char) ('a' + (charArr[i] - 'a' - key + 26) % 26);
        }
        return new String(charArr);
    }

    public int analyse(String plainText, String cipherText) {
        plainText  = plainText.toLowerCase();
        cipherText = cipherText.toLowerCase();

        int foundKey = -1;

        for (int i = 0; i < plainText.length(); i++) {
            char p = plainText.charAt(i);
            char c = cipherText.charAt(i);

            if (p < 'a' || p > 'z') continue;

            int shift = (c - p + 26) % 26;
            if (foundKey == -1) {

                foundKey = shift;

            } else if (foundKey != shift) {

                return -1;
            }
        }
        return foundKey;

//        int shift = (cipherText.charAt(0) - plainText.charAt(0) + 26) % 26;
//
//        return shift;
    }
}
