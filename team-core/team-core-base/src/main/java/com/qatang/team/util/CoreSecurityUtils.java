package com.qatang.team.util;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 安全工具集合类
 *
 * 转码工具: 编码 解码
 *  BASE64、HEX
 *
 * 消息摘要：单向散列
 *  MD5、SHA1、CRC32
 *
 * @author qatang
 */
public class CoreSecurityUtils {

    /**
     * base64编码
     * @param input 输入数据
     * @return 编码结果
     */
    public static String encodeBase64(byte[] input) {
        return BaseEncoding.base64().encode(input);
    }

    /**
     * base64解码
     * @param encodeString 已编码字符串
     * @return 解码结果
     */
    public static byte[] decodeBase64(String encodeString) {
        return BaseEncoding.base64().decode(encodeString);
    }

    /**
     * 十六进制编码
     * @param input 输入数据
     * @return 编码结果 小写字母
     */
    public static String encodeHex(byte[] input) {
        return BaseEncoding.base16().lowerCase().encode(input);
    }

    /**
     * 十六进制解码
     * @param encodeString 已编码字符串 小写字母
     * @return 解码结果
     */
    public static byte[] decodeHex(String encodeString) {
        return BaseEncoding.base16().lowerCase().decode(encodeString);
    }

    /**
     * MD5摘要
     * @param input 输入数据
     * @return 摘要结果
     */
    public static String digestMD5(byte[] input) {
        return Hashing.md5().hashBytes(input).toString();
    }

    /**
     * SHA1摘要
     * @param input 输入数据
     * @return 摘要结果
     */
    public static String digestSHA1(byte[] input) {
        return Hashing.sha1().hashBytes(input).toString();
    }

    /**
     * CRC32摘要
     * 结果与jdk自带crc32结果不一致，该方法只用于内部业务逻辑计算checksum值，不能对外业务使用
     * 参照 <a href="https://github.com/google/guava/issues/1332">https://github.com/google/guava/issues/1332</>
     * @param input 输入数据
     * @return 摘要结果
     */
    public static long digestCRC32(byte[] input) {
        return Hashing.crc32c().hashBytes(input).padToLong();
    }

    /**
     * RSA用私钥对信息生成数字签名
     *
     * @param input 源数据
     * @param encodedPrivateKey 私钥
     * @return 签名结果
     */
    public static byte[] signRSA(byte[] input, byte[] encodedPrivateKey) throws RuntimeException {
        try {
            //构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //取私钥匙对象
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //用私钥对信息生成数字签名
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(privateKey);
            signature.update(input);
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA用公钥验证数字签名
     *
     * @param input 源数据
     * @param encodedPublicKey 公钥
     * @param sign 签名
     * @return 签名结果
     */
    public static boolean verifySignRSA(byte[] input, byte[] encodedPublicKey, byte[] sign) throws RuntimeException {
        try {
            //构造X509EncodedKeySpec对象
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //取公钥匙对象
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //用公钥对信息生成数字签名
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(publicKey);
            signature.update(input);
            //验证签名是否正常
            return signature.verify(sign);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA公钥加密
     *
     * @param input 源数据
     * @param encodedPublicKey 公钥
     * @return 加密后的结果
     */
    public static byte[] encryptRSA(byte[] input, byte[] encodedPublicKey) throws RuntimeException {
        try {
            //构造X509EncodedKeySpec对象
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //取公钥匙对象
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //加密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 不能直接使用RSA，要写全转换模式，不然与android不一致
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA私钥解密
     *
     * @param input 源数据
     * @param encodePrivateKey 私钥
     * @return 解密后的结果
     */
    public static byte[] decryptRSA(byte[] input, byte[] encodePrivateKey) throws RuntimeException {
        try {
            //构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodePrivateKey);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //取公钥匙对象
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //解密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 不能直接使用RSA，要写全转换模式，不然与android不一致
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 3des加密
     * ECB模式，不需要初始化向量
     * @param input 源数据
     * @param encodedKey 私钥
     * @return 加密后的结果
     */
    public static byte[] encryptDESede(byte[] input, byte[] encodedKey) throws RuntimeException {
        try {
            //构造DESedeKeySpec对象
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(encodedKey);
            //指定加密算法
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
            //取对称密钥对象
            SecretKey secretKey = secretKeyFactory.generateSecret(deSedeKeySpec);
            //加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 3des解密
     * ECB模式，不需要初始化向量
     * @param input 源数据
     * @param encodedKey 私钥
     * @return 解密后的结果
     */
    public static byte[] decryptDESede(byte[] input, byte[] encodedKey) throws RuntimeException {
        try {
            //构造DESedeKeySpec对象
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(encodedKey);
            //指定加密算法
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
            //取公钥匙对象
            SecretKey secretKey = secretKeyFactory.generateSecret(deSedeKeySpec);
            //解密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 3des加密
     * CBC模式，需要64位初始化向量
     * @param input 源数据
     * @param encodedKey 私钥
     * @param iv 8字节初始化向量
     * @return 加密后的结果
     */
    public static byte[] encryptDESede(byte[] input, byte[] encodedKey, byte[] iv) throws RuntimeException {
        try {
            //构造DESedeKeySpec对象
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(encodedKey);
            //指定加密算法
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
            //取公钥匙对象
            SecretKey secretKey = secretKeyFactory.generateSecret(deSedeKeySpec);
            // 初始化向量
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            //加密
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 3des解密
     * CBC模式，需要64位初始化向量
     * @param input 源数据
     * @param encodedKey 私钥
     * @param iv 8字节初始化向量，与加密时保持一致
     * @return 解密后的结果
     */
    public static byte[] decryptDESede(byte[] input, byte[] encodedKey, byte[] iv) throws RuntimeException {
        try {
            //构造DESedeKeySpec对象
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(encodedKey);
            //指定加密算法
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
            //取公钥匙对象
            SecretKey secretKey = secretKeyFactory.generateSecret(deSedeKeySpec);
            // 初始化向量
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            //解密
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * aes加密
     * ECB模式，不需要初始化向量
     * @param input 源数据
     * @param encodedKey 私钥
     * @return 加密后的结果
     */
    public static byte[] encryptAES(byte[] input, byte[] encodedKey) throws RuntimeException {
        try {
            //构造SecretKeySpec对象
            SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, "AES");

            //加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * aes解密
     * ECB模式，不需要初始化向量
     * @param input 源数据
     * @param encodedKey 私钥
     * @return 解密后的结果
     */
    public static byte[] decryptAES(byte[] input, byte[] encodedKey) throws RuntimeException {
        try {
            //构造SecretKeySpec对象
            SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, "AES");
            //解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * aes加密
     * CBC模式，需要128位初始化向量
     * @param input 源数据
     * @param encodedKey 私钥
     * @param iv 16字节初始化向量
     * @return 加密后的结果
     */
    public static byte[] encryptAES(byte[] input, byte[] encodedKey, byte[] iv) throws RuntimeException {
        try {
            //构造SecretKeySpec对象
            SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, "AES");
            // 初始化向量
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            //加密
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES解密
     * CBC模式，需要128位初始化向量
     * @param input 源数据
     * @param encodedKey 私钥
     * @param iv 16字节初始化向量，与加密时保持一致
     * @return 解密后的结果
     */
    public static byte[] decryptAES(byte[] input, byte[] encodedKey, byte[] iv) throws RuntimeException {
        try {
            //构造SecretKeySpec对象
            SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, "AES");
            // 初始化向量
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            //解密
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
