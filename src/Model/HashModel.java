package Model;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.zip.CRC32;

public class HashModel {

    public String hash(String input, String algorithm) throws Exception {
        if (algorithm.equalsIgnoreCase("CRC-32")) {
            CRC32 crc = new CRC32();
            crc.update(input.getBytes("UTF-8"));
            long value = crc.getValue();
            return Long.toHexString(value).toUpperCase();
        }

        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(hashBytes);
    }

    public String hashFile(File inputFile, String algorithm) throws Exception {
        if (algorithm.equalsIgnoreCase("CRC-32")) {
            CRC32 crc = new CRC32();
            try (FileInputStream fis = new FileInputStream(inputFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    crc.update(buffer, 0, bytesRead);
                }
            }
            return Long.toHexString(crc.getValue()).toUpperCase();
        }
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        return Base64.getEncoder().encodeToString(digest.digest());
    }
}
