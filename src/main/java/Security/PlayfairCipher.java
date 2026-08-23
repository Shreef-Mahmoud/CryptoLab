package Security;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlayfairCipher {
    private final char[][] keyMatrix;

    public PlayfairCipher(String key) {
        keyMatrix = generateKeyMatrix(key);
    }

    private char[][] generateKeyMatrix(String key) {
        Set<Character> used = new LinkedHashSet<>();
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        for (char c : key.toCharArray()) used.add(c);
        for (char c = 'A'; c <= 'Z'; c++) if (c != 'J') used.add(c);
        char[][] matrix = new char[5][5];
        Iterator<Character> it = used.iterator();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matrix[i][j] = it.next();
        return matrix;
    }

    private String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (i < text.length() - 1 && text.charAt(i) == text.charAt(i + 1) && text.charAt(i) != 'X')
                sb.append('X');
        }
        if (sb.length() % 2 != 0) sb.append('X');
        return sb.toString();
    }

    private int[] findPosition(char c) {
        for (int row = 0; row < 5; row++)
            for (int col = 0; col < 5; col++)
                if (keyMatrix[row][col] == c) return new int[]{row, col};
        return null;
    }

    public String encrypt(String text) {
        text = prepareText(text);
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            int[] pos1 = findPosition(text.charAt(i));
            int[] pos2 = findPosition(text.charAt(i + 1));
            if (pos1 == null || pos2 == null) continue;
            if (pos1[0] == pos2[0]) {
                encryptedText.append(keyMatrix[pos1[0]][(pos1[1] + 1) % 5]);
                encryptedText.append(keyMatrix[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) {
                encryptedText.append(keyMatrix[(pos1[0] + 1) % 5][pos1[1]]);
                encryptedText.append(keyMatrix[(pos2[0] + 1) % 5][pos2[1]]);
            } else {
                encryptedText.append(keyMatrix[pos1[0]][pos2[1]]);
                encryptedText.append(keyMatrix[pos2[0]][pos1[1]]);
            }
        }
        return encryptedText.toString();
    }

    private String removePadding(String text) {
        StringBuilder sb = new StringBuilder(text);
        for (int i = 1; i < sb.length() - 1; i++) {
            if (sb.charAt(i) == 'X' && sb.charAt(i - 1) == sb.charAt(i + 1)) {
                sb.deleteCharAt(i);
                i--;
            }
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == 'X')
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String decrypt(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        if (text.length() % 2 != 0) text += "X";
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            int[] pos1 = findPosition(text.charAt(i));
            int[] pos2 = findPosition(text.charAt(i + 1));
            if (pos1 == null || pos2 == null) continue;
            if (pos1[0] == pos2[0]) {
                decryptedText.append(keyMatrix[pos1[0]][(pos1[1] + 4) % 5]);
                decryptedText.append(keyMatrix[pos2[0]][(pos2[1] + 4) % 5]);
            } else if (pos1[1] == pos2[1]) {
                decryptedText.append(keyMatrix[(pos1[0] + 4) % 5][pos1[1]]);
                decryptedText.append(keyMatrix[(pos2[0] + 4) % 5][pos2[1]]);
            } else {
                decryptedText.append(keyMatrix[pos1[0]][pos2[1]]);
                decryptedText.append(keyMatrix[pos2[0]][pos1[1]]);
            }
        }
        return removePadding(decryptedText.toString());
    }
}