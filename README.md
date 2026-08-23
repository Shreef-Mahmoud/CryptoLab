# CryptoLab — Classical & Modern Cipher Implementations in Java

A collection of classical and modern cryptographic algorithms implemented in Java as part of a university Security course assignment. Each cipher supports **encryption**, **decryption**, and — where applicable — **cryptanalysis** (recovering the key from a known plaintext/ciphertext pair).

## 📂 Algorithms Implemented

### Classical Ciphers
| Algorithm | Encrypt | Decrypt | Cryptanalysis |
|---|:---:|:---:|:---:|
| Caesar Cipher | ✅ | ✅ | ✅ (key recovery) |
| Monoalphabetic Substitution | ✅ | ✅ | ✅ (known-plaintext + frequency analysis) |
| Playfair Cipher | ✅ | ✅ | — |
| Hill Cipher (2×2 / 3×3) | ✅ | ✅ | ✅ (known-plaintext matrix recovery) |
| Rail Fence Cipher | ✅ | ✅ | ✅ (key/rail count recovery) |
| Columnar Transposition | ✅ | ✅ | ✅ (column order recovery) |
| Autokey Cipher | ✅ | ✅ | ✅ (key recovery) |
| Repeating-Key (Vigenère) Cipher | ✅ | ✅ | ✅ (key recovery) |

### Modern Ciphers & Public-Key Schemes
| Algorithm | Description |
|---|---|
| **DES** | Full 16-round Feistel implementation (IP/FP, expansion, S-boxes, PC-1/PC-2 key schedule, P-box) |
| **AES-128** | Full 10-round implementation (SubBytes, ShiftRows, MixColumns, key expansion) |
| **Diffie-Hellman** | Key exchange — derives the shared secret from both parties' private keys |
| **ElGamal** | Asymmetric encryption/decryption using modular exponentiation |

## 🗂️ Project Structure
```
Security/
├── CaeserCipher.java
├── MonoalphabeticCipher.java
├── PlayfairCipher.java
├── HillCipher.java
├── Railfence.java
├── ColumnarCipher.java
├── AutoKey.java
├── RepeatingKey.java
├── DES.java
├── AES.java
├── DiffieHellman.java
└── ElGamal.java
```

All classes live in the `Security` package.

## ▶️ Usage

Each cipher exposes a simple instance-based API. Example:

```java
CaeserCipher caesar = new CaeserCipher();
String cipherText = caesar.encrypt("hello world", 3);
String plainText  = caesar.decrypt(cipherText, 3);
int recoveredKey  = caesar.analyse(plainText, cipherText);
```

```java
AES aes = new AES();
String cipherHex = aes.encrypt("0x00112233445566778899aabbccddeeff",
                                "0x000102030405060708090a0b0c0d0e0f");
String plainHex  = aes.decrypt(cipherHex,
                                "0x000102030405060708090a0b0c0d0e0f");
```

```java
DiffieHellman dh = new DiffieHellman();
// q = prime modulus, alpha = primitive root, xa/xb = private keys
List<Integer> sharedKeys = dh.getKeys(q, alpha, xa, xb); // [ka, kb] — should match
```

## 🧠 Notes

- Classical ciphers operate on lowercase alphabetic text (`a`–`z`); non-letter characters are generally stripped or lowercased depending on the cipher.
- `HillCipher` supports both 2×2 and 3×3 key matrices and throws `InvalidAnalysisException` when a matrix isn't invertible mod 26.
- `AES` and `DES` operate on hex-encoded strings (`AES` expects a `0x`-prefixed 128-bit block/key; `DES` expects 64-bit hex blocks/keys).
- Cryptanalysis methods assume a known-plaintext attack scenario (both plaintext and ciphertext are given) rather than ciphertext-only attacks, except for `analyseUsingCharFrequency` in `MonoalphabeticCipher`, which is a ciphertext-only frequency-analysis heuristic.

## 🎓 Context

Built for a Security course assignment to practice implementing classical cryptanalysis techniques and the internals of modern symmetric/asymmetric algorithms (DES, AES, Diffie-Hellman, ElGamal) from scratch in Java, without relying on `javax.crypto`.
