package Security;

public class AutoKey {
    public String analyse(String plainText, String cipherText) {
        // Students should complete this part
        cipherText = cipherText.toLowerCase();
        plainText = plainText.toLowerCase();
        int len = cipherText.length();

        StringBuilder fullKeystream = new StringBuilder();


        for (int i = 0; i < len; i++) {
            int c = cipherText.charAt(i) - 'a';
            int p = plainText.charAt(i) - 'a';
            char k = (char) (((c - p + 26) % 26) + 'a');
            fullKeystream.append(k);
        }

        String fullKeyStr = fullKeystream.toString();

        for (int i = 1; i < len; i++) {
            if (fullKeyStr.substring(i).equals(plainText.substring(0, len - i))) {
                return fullKeyStr.substring(0, i);
            }
        }

        return fullKeyStr;
    }

    public String decrypt(String cipherText, String key) {
        // Students should complete this part
        cipherText = cipherText.toLowerCase();
        key = key.toLowerCase();
        int len = cipherText.length();

        StringBuilder autoKey = new StringBuilder(key);
        StringBuilder plainText = new StringBuilder();

        for (int i = 0; i < len; i++) {

            int c = cipherText.charAt(i) - 'a';
            int k = autoKey.charAt(i) - 'a';

            char p = (char)(((c - k + 26) % 26) + 'a');
            plainText.append(p);

            autoKey.append(p);
        }

        return plainText.toString();
    }

    public String encrypt(String plainText, String key) {
        plainText = plainText.toLowerCase();
        key = key.toLowerCase();
        int len = plainText.length();

        // Extend key using the plaintext
        StringBuilder autoKey = new StringBuilder(key);
        if (autoKey.length() < len) {
            int diffLen = len - autoKey.length();
            for (int i = 0; i < diffLen; i++) {
                autoKey.append(plainText.charAt(i));
            }
        }

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int p = plainText.charAt(i) - 'a';
            int k = autoKey.charAt(i) - 'a';
            cipherText.append((char) (((p + k) % 26) + 'a'));
        }
        return cipherText.toString();
    }
}
