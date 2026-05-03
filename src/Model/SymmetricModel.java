package Model;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;

public class SymmetricModel {
    // su dung thu vien ngoai bouncy castle provider
    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private static String[] resolveAlgorithm(String algorithm)  {
        // name, length, provider
        switch (algorithm) {
            case "AES": return new String[] {"AES", "16", "default"};
            case "DES": return new String[] {"DES", "8", "default"};
            case "Blowfish": return new String[] {"Blowfish", "16", "default"};
            case "TripleDES":
            case "DESede": return new String[] {"DESede", "24", "default"};
            case "CAST6": return new String[] {"CAST6", "16", "BC"};
            case "Twofish": return new String[] {"Twofish", "16", "BC"};
            default:    return new String[] {algorithm, "16", "default"};
        }
    }

    private static byte[] prepareKey(String keyString, int keyLength) throws Exception {
        byte[] keyBytes = keyString.getBytes("UTF-8");
        byte[] validKey = new byte[keyLength];
        System.arraycopy(keyBytes,0, validKey, 0, Math.min(keyBytes.length,validKey.length));
        return validKey;

    }

    private static String encryptJCA(String plaintext, String keyString, String algorithm) throws Exception {
        String[] meta = resolveAlgorithm(algorithm);
        String nameAlgorithm = meta[0];
        int keyLength = Integer.parseInt(meta[1]);
        String provider = meta[2];

        byte[] validKey = prepareKey(keyString, keyLength);
        SecretKeySpec secretKey = new SecretKeySpec(validKey, nameAlgorithm);

        Cipher cipher;
        if ("BC".equals(provider)) {
            cipher = Cipher.getInstance(nameAlgorithm + "/ECB/PKCS5Padding", "BC");

        } else {
            cipher = Cipher.getInstance(nameAlgorithm + "/ECB/PKCS5Padding");
        }

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encrypted = cipher.doFinal(plaintext.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);

    }

    private static String decryptJCA(String ciphertext, String keyString, String algorithm) throws Exception {
        String[] meta = resolveAlgorithm(algorithm);
        String nameAlgorithm = meta[0];
        int keyLength = Integer.parseInt(meta[1]);
        String provider = meta[2];

        byte[] validKey = prepareKey(keyString, keyLength);
        SecretKeySpec secretKey = new SecretKeySpec(validKey, nameAlgorithm);

        Cipher cipher;

        if ("BC".equals(provider)) {
            cipher = Cipher.getInstance(nameAlgorithm + "/ECB/PKCS5Padding", "BC");
        } else {
            cipher = Cipher.getInstance(nameAlgorithm + "/ECB/PKCS5Padding");
        }
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decrypted, "UTF-8");
    }

    private static String encryptVigenere(String plaintext, String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Khóa Vigenere không được rỗng.");
        }
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                boolean isUpperCase = Character.isUpperCase(c);
                int p = Character.toUpperCase(c) - 'A';
                int k = key.charAt(keyIndex % key.length()) - 'A';
                int encryptedChar = (p + k) % 26 + 'A';
                result.append(isUpperCase ? (char) encryptedChar : Character.toLowerCase((char) encryptedChar));
                keyIndex++;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private static String decryptVigenere(String ciphertext, String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Khóa Vigenere không được rỗng.");
        }
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                boolean isUpperCase = Character.isUpperCase(c);
                int p = Character.toUpperCase(c) - 'A';
                int k = key.charAt(keyIndex % key.length()) - 'A';
                int decryptedChar = (p - k + 26) % 26 + 'A';
                result.append(isUpperCase ? (char) decryptedChar : Character.toLowerCase((char) decryptedChar));
                keyIndex++;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private static String encryptHill(String plaintext, String key) throws Exception {
        if (key.length() < 4) {
            throw new Exception("Khóa Hill cần ít nhất 4 ký tự chữ cái.");
        }
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        if (key.length() < 4) {
            throw new Exception("Khóa Hill cần ít nhất 4 ký tự chữ cái (A-Z).");
        }

        int[][] kMap = {
                {key.charAt(0) - 'A', key.charAt(1) - 'A'},
                {key.charAt(2) - 'A', key.charAt(3) - 'A'}
        };

        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        if (plaintext.isEmpty()) {
            throw new Exception("Văn bản Hill không được rỗng sau khi lọc ký tự.");
        }
        if (plaintext.length() % 2 != 0) {
            plaintext += "X"; // padding
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            int p1 = plaintext.charAt(i) - 'A';
            int p2 = plaintext.charAt(i + 1) - 'A';
            int c1 = (kMap[0][0] * p1 + kMap[0][1] * p2) % 26;
            int c2 = (kMap[1][0] * p1 + kMap[1][1] * p2) % 26;
            result.append((char) (c1 + 'A')).append((char) (c2 + 'A'));
        }
        return result.toString();
    }

    private static String decryptHill(String ciphertext, String key) throws Exception {
        if (key.length() < 4) {
            throw new Exception("Khóa Hill cần ít nhất 4 ký tự chữ cái.");
        }
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        if (key.length() < 4) {
            throw new Exception("Khóa Hill cần ít nhất 4 ký tự chữ cái (A-Z).");
        }

        int k11 = key.charAt(0) - 'A';
        int k12 = key.charAt(1) - 'A';
        int k21 = key.charAt(2) - 'A';
        int k22 = key.charAt(3) - 'A';

        int det = ((k11 * k22 - k12 * k21) % 26 + 26) % 26;

        // Tìm nghịch đảo modulo 26 của định thức
        int detInv = -1;
        for (int i = 1; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInv = i;
                break;
            }
        }
        if (detInv == -1) {
            throw new Exception("Khóa không hợp lệ: ma trận không có nghịch đảo modulo 26 (det=" + det + ").");
        }

        int inv11 = ( k22 * detInv) % 26;
        int inv12 = (-k12 * detInv % 26 + 26) % 26;
        int inv21 = (-k21 * detInv % 26 + 26) % 26;
        int inv22 = ( k11 * detInv) % 26;

        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        if (ciphertext.length() % 2 != 0) {
            throw new Exception("Bản mã Hill không hợp lệ: độ dài phải chẵn.");
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            int c1 = ciphertext.charAt(i) - 'A';
            int c2 = ciphertext.charAt(i + 1) - 'A';
            int p1 = (inv11 * c1 + inv12 * c2) % 26;
            int p2 = (inv21 * c1 + inv22 * c2) % 26;
            result.append((char) (p1 + 'A')).append((char) (p2 + 'A'));
        }
        return result.toString();
    }

    public String encrypt(String plaintext, String key, String algorithm) throws Exception {
        switch (algorithm) {
            case "Vigenere": return encryptVigenere(plaintext, key);
            case "Hill":     return encryptHill(plaintext, key);
            default:         return encryptJCA(plaintext, key, algorithm);
        }
    }

    public String decrypt(String ciphertext, String key, String algorithm) throws Exception {
        switch (algorithm) {
            case "Vigenere": return decryptVigenere(ciphertext, key);
            case "Hill":     return decryptHill(ciphertext, key);
            default:         return decryptJCA(ciphertext, key, algorithm);
        }
    }

    public void encryptFile(java.io.File inputFile, java.io.File outputFile, String keyString, String algorithm) throws Exception {
        if (algorithm.equalsIgnoreCase("Vigenere") || algorithm.equalsIgnoreCase("Hill")) {
            throw new Exception("Thuật toán " + algorithm + " chỉ hỗ trợ văn bản, không hỗ trợ File.");
        }
        String[] meta = resolveAlgorithm(algorithm);
        String nameAlgorithm = meta[0];
        int keyLength = Integer.parseInt(meta[1]);
        String provider = meta[2];

        byte[] validKey = prepareKey(keyString, keyLength);
        SecretKeySpec secretKey = new SecretKeySpec(validKey, nameAlgorithm);

        Cipher cipher;
        if ("BC".equals(provider)) {
            cipher = Cipher.getInstance(nameAlgorithm + "/ECB/PKCS5Padding", "BC");
        } else {
            cipher = Cipher.getInstance(nameAlgorithm + "/ECB/PKCS5Padding");
        }
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        try (java.io.FileInputStream fis = new java.io.FileInputStream(inputFile);
             java.io.FileOutputStream fos = new java.io.FileOutputStream(outputFile);
             javax.crypto.CipherOutputStream cos = new javax.crypto.CipherOutputStream(fos, cipher)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }
    }

    public void decryptFile(java.io.File inputFile, java.io.File outputFile, String keyString, String algorithm) throws Exception {
        if (algorithm.equalsIgnoreCase("Vigenere") || algorithm.equalsIgnoreCase("Hill")) {
            throw new Exception("Thuật toán " + algorithm + " chỉ hỗ trợ văn bản, không hỗ trợ File.");
        }
        String[] meta = resolveAlgorithm(algorithm);
        String nameAlgorithm = meta[0];
        int keyLength = Integer.parseInt(meta[1]);
        String provider = meta[2];

        byte[] validKey = prepareKey(keyString, keyLength);
        SecretKeySpec secretKey = new SecretKeySpec(validKey, nameAlgorithm);

        Cipher cipher;
        if ("BC".equals(provider)) {
            cipher = Cipher.getInstance(nameAlgorithm + "/ECB/PKCS5Padding", "BC");
        } else {
            cipher = Cipher.getInstance(nameAlgorithm + "/ECB/PKCS5Padding");
        }
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (java.io.FileInputStream fis = new java.io.FileInputStream(inputFile);
             javax.crypto.CipherInputStream cis = new javax.crypto.CipherInputStream(fis, cipher);
             java.io.FileOutputStream fos = new java.io.FileOutputStream(outputFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

}
