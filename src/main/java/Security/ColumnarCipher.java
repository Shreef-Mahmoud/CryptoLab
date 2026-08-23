package Security;
import java.util.*;

public class ColumnarCipher {

    public List<Integer> analyse(String plainText, String cipherText) {
        // TODO: Analyze the plainText and cipherText to determine the key(s)
        plainText = plainText.replace(" ", "").toLowerCase();
        cipherText = cipherText.replace(" ", "").toLowerCase();

        int ptSize = plainText.length();
        int count = 1;

        while(count <= ptSize)
        {

            int rows = (int)Math.ceil((double)ptSize / (double)count);
            char[][] grid = new char[rows][count];

            int count1 = 0;

            for(int i = 0; i < rows; ++i) {
                for(int j = 0; j < count; ++j) {
                    if (count1 >= ptSize) {
                        grid[i][j] = 'x';
                    } else {
                        grid[i][j] = plainText.charAt(count1++);
                    }
                }
            }

            List<Integer> key = new ArrayList<>();
            boolean valid = true;
            boolean[] used = new boolean[count];

            for(int c = 0; c < count; c++)
            {
                StringBuilder col = new StringBuilder();

                for(int r = 0; r < rows; r++)
                {
                    col.append(grid[r][c]);
                }

                String column = col.toString();

                boolean found = false;

                for(int block = 0; block < count; block++)
                {
                    int start = block * rows;
                    int end = Math.min(start + rows, cipherText.length());

                    String part = cipherText.substring(start, end);

                    if(!used[block] && part.equals(column))
                    {
                        key.add(block+1);
                        used[block] = true;
                        found = true;
                        break;
                    }
                }

                if(!found)
                {
                    valid = false;
                    break;
                }
            }

            if(valid && key.size() == count)
            {
                return key;
            }

            count++;
        }

        return new ArrayList<>();// Placeholder return
    }

    public String decrypt(String cipherText, List<Integer> key) {
        int cipherSize = cipherText.length();
        int rows = (int) Math.ceil((double) cipherSize / key.size());
        char[][] grid = new char[rows][key.size()];
        int count = 0;

        Map<Integer, Integer> keyMap = new HashMap<>();
        for (int i = 0; i < key.size(); i++) {
            keyMap.put(key.get(i) - 1, i);
        }

        int remainingCols = cipherSize % key.size();
        for (int i = 0; i < key.size(); i++) {
            for (int j = 0; j < rows; j++) {
                if (remainingCols != 0 && j == rows - 1 && keyMap.get(i) >= remainingCols) continue;
                grid[j][keyMap.get(i)] = cipherText.charAt(count++);
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.size(); j++) {
                result.append(grid[i][j]);
            }
        }
        return result.toString().toUpperCase().trim();
    }

    public String encrypt(String plainText, List<Integer> key) {
        int ptSize = plainText.length();
        int rows = (int) Math.ceil((double) ptSize / key.size());
        char[][] grid = new char[rows][key.size()];
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.size(); j++) {
                if (count >= ptSize) {
                    grid[i][j] = 'x';
                } else {
                    grid[i][j] = plainText.charAt(count++);
                }
            }
        }

        Map<Integer, Integer> keyMap = new HashMap<>();
        for (int i = 0; i < key.size(); i++) {
            keyMap.put(key.get(i) - 1, i);
        }

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < key.size(); i++) {
            for (int j = 0; j < rows; j++) {
                cipherText.append(Character.toUpperCase(grid[j][keyMap.get(i)]));
            }
        }
        return cipherText.toString();
    }
}
