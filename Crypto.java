import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class Crypto {
    private static final String ALGORITHM = "AES";

    public static void encryptFile(String sourceFilePath, String encryptedFilePath, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        ProcessCipher(sourceFilePath, encryptedFilePath, cipher);
    }

    public static void decryptFile(String encryptedFilePath, String decryptedFilePath, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        ProcessCipher(encryptedFilePath, decryptedFilePath, cipher);
    }

    private static void ProcessCipher(String encryptedFilePath, String decryptedFilePath, Cipher cipher) throws IOException, IllegalBlockSizeException, BadPaddingException {
        try (InputStream inputStream = new FileInputStream(encryptedFilePath);
             OutputStream outputStream = new FileOutputStream(decryptedFilePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] decryptedBytes = cipher.update(buffer, 0, bytesRead);
                outputStream.write(decryptedBytes);
            }
            byte[] finalDecryptedBytes = cipher.doFinal();
            outputStream.write(finalDecryptedBytes);
        }
    }
}
