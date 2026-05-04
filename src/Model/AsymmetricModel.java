package Model;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AsymmetricModel {

    public KeyPair generateRSAKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keySize);
        return keyGen.generateKeyPair();
    }

    public String encryptRSA(String plaintext, String publicKeyStr) throws Exception {
        byte[] aesKey = new byte[32];
        new SecureRandom().nextBytes(aesKey);
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(aesKey, "AES"), new IvParameterSpec(iv));
        byte[] encryptedData = aesCipher.doFinal(plaintext.getBytes("UTF-8"));

        byte[] keyAndIv =new byte[48];
        System.arraycopy(aesKey, 0, keyAndIv, 0,32);
        System.arraycopy(iv, 0, keyAndIv,32, 16);

        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);

        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKeyAndIv = rsaCipher.doFinal(keyAndIv);

        ByteBuffer buffer = ByteBuffer.allocate(4 + encryptedKeyAndIv.length + encryptedData.length);
        buffer.putInt(encryptedKeyAndIv.length);
        buffer.put(encryptedKeyAndIv);
        buffer.put(encryptedData);

        return Base64.getEncoder().encodeToString(buffer.array());

    }

    public String decryptRSA(String ciphertext, String privateKeyStr) throws Exception {
        byte[] combined = Base64.getDecoder().decode(ciphertext);
        ByteBuffer buffer = ByteBuffer.wrap(combined);

        int rsaBlockLength = buffer.getInt();
        byte[] encryptedKeyAndIv = new byte[rsaBlockLength];
        buffer.get(encryptedKeyAndIv);

        byte[] encryptedData = new byte[buffer.remaining()];
        buffer.get(encryptedData);

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(spec);

        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] keyAndIv = rsaCipher.doFinal(encryptedKeyAndIv);

        byte[] aesKey = new byte[32];
        byte[] iv = new byte[16];
        System.arraycopy(keyAndIv,0, aesKey,0, 32);
        System.arraycopy(keyAndIv,32, iv, 0, 16);

        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(aesKey, "AES"), new IvParameterSpec(iv));
        byte[] decryptedData = aesCipher.doFinal(encryptedData);

        return new String(decryptedData, "UTF-8");

    }

    public String getPublicKeyString(PublicKey publicKey){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String getPrivateKeyString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public void encryptFileRSA(File inputFile, File outputFile, String publicKeyStr) throws Exception {
        byte[] aesKey = new byte[32];
        new SecureRandom().nextBytes(aesKey);
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        byte[] keyAndIv = new byte[48];
        System.arraycopy(aesKey, 0, keyAndIv, 0, 32);
        System.arraycopy(iv, 0, keyAndIv, 32, 16);

        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);

        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKeyAndIv = rsaCipher.doFinal(keyAndIv);

        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(aesKey, "AES"), new IvParameterSpec(iv));

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos= new FileOutputStream(outputFile);
             DataOutputStream dos = new DataOutputStream(fos)) {

            dos.writeInt(encryptedKeyAndIv.length);
            dos.write(encryptedKeyAndIv);

            try (CipherOutputStream cos = new CipherOutputStream(fos, aesCipher)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    cos.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    public void decryptFileRSA(File inputFile, File outputFile, String privateKeyStr) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputFile);
             DataInputStream dis = new DataInputStream(fis)) {

            int rsaBlockLength = dis.readInt();
            byte[] encryptedKeyAndIv = new byte[rsaBlockLength];
            dis.readFully(encryptedKeyAndIv);

            byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(spec);

            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] keyAndIv = rsaCipher.doFinal(encryptedKeyAndIv);

            byte[] aesKey = new byte[32];
            byte[] iv= new byte[16];
            System.arraycopy(keyAndIv, 0, aesKey,0,32);
            System.arraycopy(keyAndIv,32,iv,0,16);

            Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(aesKey, "AES"), new IvParameterSpec(iv));

            try (CipherInputStream cis = new CipherInputStream(fis, aesCipher);
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = cis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
        }
    }




}
