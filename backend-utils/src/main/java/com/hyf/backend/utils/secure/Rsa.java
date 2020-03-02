package com.hyf.backend.utils.secure;

import com.hyf.backend.utils.exception.CryptoException;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA非对称加密算法
 *
 * @Author: Elvis on 2019/12/4
 * @Email: yfelvis@gmail.com
 * @Desc: 非对称加密算法
 */
public final class Rsa {

    private String algorithm;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Cipher cipher;

    private Rsa(String algorithm, byte[] privateKey, byte[] publicKey) {
        this.algorithm = algorithm;
        init(algorithm, generatePrivateKey(algorithm, privateKey), generatePublicKey(algorithm, publicKey));
    }

    /**
     * 初始化RSA
     *
     * @param algorithm  算法类型
     * @param privateKey 公钥
     * @param publicKey  私钥
     */
    private void init(String algorithm, PrivateKey privateKey, PublicKey publicKey) {
        if (null == privateKey && null == publicKey) {
            KeyPairGenerator keyPairGenerator;
            try {
                keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                throw new CryptoException(e);
            }
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();
        }
        if (null != privateKey) {
            this.privateKey = privateKey;
        }

        if (null != publicKey) {
            this.publicKey = publicKey;
        }
        try {
            this.cipher = Cipher.getInstance(algorithm);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 不指定公钥和私钥，随机生成公钥和私钥
     *
     * @return RSA
     */
    public static Rsa createRsa() {
        return new Rsa("RSA", (byte[]) null, (byte[]) null);
    }

    /**
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return RSA对象
     */
    public static Rsa createRsa(byte[] privateKey, byte[] publicKey) {
        return new Rsa("RSA", privateKey, publicKey);
    }

    /**
     * 不指定公钥和私钥，随机生成公钥和私钥
     *
     * @return RSA
     */
    public static Rsa createRsaEcbPkcs1() {
        return new Rsa("RSA/ECB/PKCS1Padding", (byte[]) null, (byte[]) null);
    }

    /**
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return RSA对象本身
     */
    public static Rsa createRsaEcbPkcs1(byte[] privateKey, byte[] publicKey) {
        return new Rsa("RSA/ECB/PKCS1Padding", privateKey, publicKey);
    }

    /**
     * 生成JDK私钥
     *
     * @param algorithm  算法类型
     * @param privateKey 私钥字节数组
     * @return JDK中的PrivateKey
     */
    private PrivateKey generatePrivateKey(String algorithm, byte[] privateKey) {
        if (privateKey == null) {
            return null;
        }
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        try {
            return KeyFactory.getInstance(algorithm).generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * @param algorithm 算法类型
     * @param publicKey 公钥字节数组
     * @return JDK中公钥
     */
    private PublicKey generatePublicKey(String algorithm, byte[] publicKey) {
        if (publicKey == null) {
            return null;
        }
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
        try {
            return KeyFactory.getInstance(algorithm).generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * @param data    要加密的字节数组
     * @param keyType key的类型枚举
     * @return 加密后的字节数组
     */
    public byte[] encrypt(byte[] data, KeyType keyType) {
        Key key = getKeyType(keyType);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * @param data    加密后的字节数组
     * @param keyType key的类型枚举
     * @return 解密后的字节数组
     */
    public byte[] decrypt(byte[] data, KeyType keyType) {
        Key key = getKeyType(keyType);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * @param type Key的类型枚举
     * @return 具体的Key类型
     */
    public Key getKeyType(KeyType type) {
        switch (type) {
            case PRIVATE_KEY:
                if (null == this.privateKey) {
                    throw new NullPointerException("Private key must not null when use it !");
                }
                return this.privateKey;
            case PUBLIC_KEY:
                if (null == this.publicKey) {
                    throw new NullPointerException("Public key must not null when use it !");
                }
                return this.publicKey;
            default:
                break;
        }
        throw new CryptoException("Uknown key type: " + type);
    }

    /**
     * 密钥类型
     */
    enum KeyType {
        //私钥类型
        PRIVATE_KEY,
        //公钥类型
        PUBLIC_KEY,
    }
}
