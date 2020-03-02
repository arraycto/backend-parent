package com.hyf.backend.utils.secure;

import com.hyf.backend.utils.exception.CryptoException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Elvis on 2019/12/6
 * @Email: yfelvis@gmail.com
 * @Desc: 对称加密AES
 */
public final class Aes {
    /**
     * 密钥
     */
    private SecretKey secretKey;

    private Cipher cipher;

    private static final int CONSTANT1 = 128;

    public Aes() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(CONSTANT1);
            this.secretKey = keyGenerator.generateKey();
            this.cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 利用JDK中的SecretKey构造
     *
     * @param secretKey 密钥
     */
    public Aes(SecretKey secretKey) {
        this.secretKey = secretKey;
        try {
            this.cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 利用自己提供的密钥
     *
     * @param secretKey 密钥
     */
    public Aes(byte[] secretKey) {
        this.secretKey = new SecretKeySpec(secretKey, "AES");
        try {
            this.cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 加密
     *
     * @param data 要加密的字节数组
     * @return 加密后的字节数组
     */
    public byte[] encrypt(byte[] data) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 加密
     *
     * @param data 要加密的字符串资源
     * @return 加密后的16进制字符串
     */
    public String encrypt(String data) {
        byte[] encrypt = encrypt(data.getBytes(StandardCharsets.UTF_8));
        return HexUtil.encodeHexStr(encrypt, true);
    }


    /**
     * 解密
     *
     * @param data 加密的字符串的16进制字符串表示
     * @return 解密后的字符串
     */
    public String decrypt(String data) {
        byte[] bytes = HexUtil.decodeHex(data);
        byte[] decrypt = decrypt(bytes);
        return new String(decrypt);
    }

    /**
     * @param data 加密的字节数组
     * @return 解密后的字节数组
     */
    public byte[] decrypt(byte[] data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }
}
