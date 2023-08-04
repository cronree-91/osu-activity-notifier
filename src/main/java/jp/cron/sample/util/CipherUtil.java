package jp.cron.sample.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CipherUtil {

    public static String encodeAES(String src, String secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(HashUtil.sha256Bytes(secret), "AES"));
            byte[] bytes = cipher.doFinal(src.getBytes());
            return new String(Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decodeAES(String src, String secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(HashUtil.sha256Bytes(secret), "AES"));
            byte[] byteToken = Base64.getDecoder().decode(src);

            return new String(cipher.doFinal(byteToken));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
