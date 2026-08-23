package Security;

import java.util.List;

public class ElGamal {
    public List<Long> encrypt(int q, int alpha, int y, int k, int m) {
        long c1 = modPow(alpha, k, q);
        long s = modPow(y, k, q);
        long c2 = (m * s) % q;
        return List.of(c1, c2);
    }

    public int decrypt(int c1, int c2, int x, int q) {
        int k =(int)modPow(c1, x, q);
        int kInverse = (int)modPow(k, q - 2, q);
        int m =(c2 * kInverse) % q;
        return m;
    }

    public static long modPow(long base, long exp, long mod) {
        long result = 1;
        base = base % mod;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            exp >>= 1;
            base = (base * base) % mod;
        }
        return result;
    }
}
