package Security;

import java.util.List;


public class DiffieHellman {
    public List<Integer> getKeys(int q, int alpha, int xa, int xb) {
       int ya = (int)modPow(alpha,xa,q) ;
       int yb = (int)modPow(alpha,xb,q);
       int ka = (int)modPow(yb,xa,q);
       int kb = (int)modPow(ya,xb,q);
       return List.of(ka, kb);
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
