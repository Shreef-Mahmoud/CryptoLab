package Security;

public class RepeatingKey {
    public String analyse(String plainText, String cipherText) {
        // Students should complete this part
        plainText = plainText.toLowerCase();
        cipherText = cipherText.toLowerCase();

        String derivedKey = deriveKeyStream(plainText, cipherText);

        // Find the shortest repeating unit in the derived key
        for (int len = 1; len <= derivedKey.length() / 2; len++) {
            String candidate = derivedKey.substring(0, len);
            boolean repeats = true;
            for (int i = len; i < derivedKey.length(); i++) {
                if (derivedKey.charAt(i) != candidate.charAt(i % len)) {
                    repeats = false;
                    break;
                }
            }
            if (repeats) return candidate;
        }

        return derivedKey;
    }

    private String deriveKeyStream(String plainText, String cipherText) {
        int maxKeyLength = Math.min(20, plainText.length());
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < maxKeyLength; i++) {
            int p = plainText.charAt(i) - 'a';
            int c = cipherText.charAt(i) - 'a';
            int k = (c - p + 26) % 26;
            key.append((char) (k + 'a'));
        }

        return key.toString();
    }

    public String decrypt(String cipherText, String key) {
        // Students should complete this part
        cipherText = cipherText.toLowerCase();
        key = key.toLowerCase();
        int cipherLen = cipherText.length();

        // Repeat key to match ciphertext length
        StringBuilder extendedKey = new StringBuilder(key);
        while (extendedKey.length() < cipherLen) {
            extendedKey.append(extendedKey.charAt(extendedKey.length() - key.length()));
        }

        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < cipherLen; i++) {
            int c = cipherText.charAt(i) - 'a';
            int k = extendedKey.charAt(i) - 'a';
            plainText.append((char) (((c - k + 26) % 26) + 'a'));
        }

        return plainText.toString();
    }

    public String encrypt(String plainText, String key) {
        plainText = plainText.toLowerCase();
        key = key.toLowerCase();
        int plainLen = plainText.length();

        // Repeat key to match plaintext length
        StringBuilder extendedKey = new StringBuilder(key);
        while (extendedKey.length() < plainLen) {
            extendedKey.append(extendedKey.charAt(extendedKey.length() - key.length()));
        }

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < plainLen; i++) {
            int p = plainText.charAt(i) - 'a';
            int k = extendedKey.charAt(i) - 'a';
            cipherText.append((char) (((p + k) % 26) + 'a'));
        }

        return cipherText.toString();
    }
}
