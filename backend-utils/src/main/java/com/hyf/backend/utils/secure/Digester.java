package com.hyf.backend.utils.secure;

import com.hyf.backend.utils.exception.CryptoException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Elvis on 2019/12/4
 * @Email: yfelvis@gmail.com
 * @Desc: 生成摘要工具类，通过setSalt设置盐值，通过setSaltPosition设置加盐的位置
 * MD5，SHA-1，SHA-256，SHA-512
 */
public class Digester {

    private static MessageDigest m;
    /**
     * 盐值
     */
    private byte[] salt;
    /**
     * 加盐位置
     */
    private int saltPosition;


    /**
     * 创建MD5
     *
     * @return 返回MD5Digester
     */
    public static Digester createMd5Digest() {
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
        return new Digester();
    }

    /**
     * 创建Sha256
     *
     * @return 返回Sha256Digester
     */
    public static Digester createSha256Digest() {
        try {
            m = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
        return new Digester();
    }

    /**
     * 创建Sha512
     *
     * @return 返回Sha512Digester
     */
    public static Digester createSha512Digest() {
        try {
            m = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
        return new Digester();
    }

    /**
     * 设置加盐的位置，只有盐值存在时有效<br>
     * 加盐的位置指盐位于数据byte数组中的位置，例如：
     *
     * <pre>
     * data: 0123456
     * </pre>
     * <p>
     * 则当saltPosition = 2时，盐位于data的1和2中间，即第二个空隙，即：
     *
     * <pre>
     * data: 01[salt]23456
     * </pre>
     *
     * @param saltPosition 盐的位置
     * @return this
     */
    public Digester setSaltPosition(int saltPosition) {
        this.saltPosition = saltPosition;
        return this;
    }

    public byte[] getSalt() {
        return salt;
    }

    /**
     * 设置加盐内容
     *
     * @param salt 盐值
     * @return this
     */
    public Digester setSalt(byte[] salt) {
        this.salt = salt;
        return this;
    }

    public int getSaltPosition() {
        return saltPosition;
    }

    /**
     * 生成摘要
     *
     * @param datas 数据bytes
     * @return 摘要bytes
     */
    private byte[] doDigest(byte[]... datas) {
        for (byte[] data : datas) {
            if (null != data) {
                m.update(data);
            }
        }
        return m.digest();
    }

    /**
     * 普通加密
     *
     * @param data 要加密的字符串值
     * @return 加密后的16进制字符串
     */
    public String getStringDigest(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        m.update(bytes);
        return HexUtil.encodeHexStr(m.digest(), true);
    }


    /**
     * 盐值加密
     *
     * @param data 要加密的字符串值
     * @return 盐值加密后的16进制字符串
     */
    public String getStringDigestWithSalt(String data) {
        byte[] result;
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        if (this.saltPosition <= 0) {
            // 加盐在开头，自动忽略空盐值
            result = this.doDigest(this.salt, bytes);
        } else if (this.saltPosition >= bytes.length) {
            // 加盐在末尾，自动忽略空盐值
            result = this.doDigest(bytes, this.salt);
        } else if (this.salt != null && this.salt.length != 0) {
            // 加盐在中间
            m.update(bytes, 0, this.saltPosition);
            m.update(this.salt);
            m.update(bytes, this.saltPosition, bytes.length - this.saltPosition);
            result = m.digest();
        } else {
            // 无加盐
            result = this.doDigest(bytes);
        }
        return HexUtil.encodeHexStr(result, true);
    }
}
