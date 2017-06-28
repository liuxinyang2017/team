package com.qatang.team.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 安全工具集合测试类
 *
 * @author qatang
 */
public class CoreSecurityUtilsTest {

    private ThreadLocal<String> publicKeyThreadLocal = new ThreadLocal<>();

    private ThreadLocal<String> privateKeyThreadLocal = new ThreadLocal<>();

    private ThreadLocal<String> desedeKeyThreadLocal = new ThreadLocal<>();

    private ThreadLocal<String> aesKeyThreadLocal = new ThreadLocal<>();

    @Before
    public void generateRSATest() throws Exception {
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            //公钥
            PublicKey publicKey = keyPair.getPublic();
            System.out.println(String.format("publicKey algorithm : %s", publicKey.getAlgorithm()));
            System.out.println(String.format("publicKey format : %s", publicKey.getFormat()));
            //私钥
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println(String.format("privateKey algorithm : %s", privateKey.getAlgorithm()));
            System.out.println(String.format("privateKey format : %s", privateKey.getFormat()));

            String publicKeyStr = CoreSecurityUtils.encodeBase64(publicKey.getEncoded());
            publicKeyThreadLocal.set(publicKeyStr);

            String privateKeyStr = CoreSecurityUtils.encodeBase64(privateKey.getEncoded());
            privateKeyThreadLocal.set(privateKeyStr);
        }

        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println(String.format("DESede Key algorithm : %s", secretKey.getAlgorithm()));
            System.out.println(String.format("DESede Key format : %s", secretKey.getFormat()));

            String secretKeyStr = CoreSecurityUtils.encodeBase64(secretKey.getEncoded());
            desedeKeyThreadLocal.set(secretKeyStr);
        }

        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println(String.format("AES Key algorithm : %s", secretKey.getAlgorithm()));
            System.out.println(String.format("AES Key format : %s", secretKey.getFormat()));

            String secretKeyStr = CoreSecurityUtils.encodeBase64(secretKey.getEncoded());
            aesKeyThreadLocal.set(secretKeyStr);
        }
    }

    /**
     * base64编码测试
     */
    @Test
    public void encodeBase64() {
        String inputString = "测试abc123";
        String rightResult = "5rWL6K+VYWJjMTIz";
        String result = CoreSecurityUtils.encodeBase64(inputString.getBytes());
        Assert.assertTrue(rightResult.equals(result));
    }

    /**
     * base64解码测试
     */
    @Test
    public void decodeBase64() {
        String inputString = "5rWL6K+VYWJjMTIz";
        String rightResult = "测试abc123";
        byte[] result = CoreSecurityUtils.decodeBase64(inputString);
        Assert.assertTrue(rightResult.equals(new String(result)));
    }

    /**
     * 十六进制编码测试
     */
    @Test
    public void encodeHex() {
        String inputString = "测试abc123";
        String rightResult = "e6b58be8af95616263313233";
        String result = CoreSecurityUtils.encodeHex(inputString.getBytes());
        Assert.assertTrue(rightResult.equals(result));
    }

    /**
     * 十六进制解码测试
     */
    @Test
    public void decodeHex() {
        String inputString = "e6b58be8af95616263313233";
        String rightResult = "测试abc123";
        byte[] result = CoreSecurityUtils.decodeHex(inputString);
        Assert.assertTrue(rightResult.equals(new String(result)));
    }

    /**
     * MD5摘要
     */
    @Test
    public void digestMD5() {
        String inputString = "测试abc123";
        String rightResult = "7922c1b52d7047c55139da5ece7c4096";
        String result = CoreSecurityUtils.digestMD5(inputString.getBytes());
        Assert.assertTrue(rightResult.equals(result));
    }

    /**
     * SHA1摘要测试
     */
    @Test
    public void digestSHA1() {
        String inputString = "测试abc123";
        String rightResult = "5618437047ae57e26a0b042e54c156c0396d89ac";
        String result = CoreSecurityUtils.digestSHA1(inputString.getBytes());
        Assert.assertTrue(rightResult.equals(result));
    }

    /**
     * CRC32摘要测试
     */
    @Test
    public void digestCRC32() {
        String inputString = "测试abc123";
        long rightResult = 3782298985L;
        long result = CoreSecurityUtils.digestCRC32(inputString.getBytes());
        Assert.assertTrue(rightResult == result);
    }

    /**
     * RSA私钥签名与公钥验签测试
     */
    @Test
    public void signRSA() {
        String inputString = "测试abc123";

        byte[] encodedPublicKey = CoreSecurityUtils.decodeBase64(publicKeyThreadLocal.get());
        byte[] encodedPrivateKey = CoreSecurityUtils.decodeBase64(privateKeyThreadLocal.get());

        // 私钥签名
        byte[] result = CoreSecurityUtils.signRSA(inputString.getBytes(), encodedPrivateKey);
        System.out.println(CoreSecurityUtils.encodeBase64(result));

        // 公钥验签
        boolean verifyResult = CoreSecurityUtils.verifySignRSA(inputString.getBytes(), encodedPublicKey, result);
        Assert.assertTrue(verifyResult);
    }

    /**
     * RSA公钥加密与私钥解密测试
     */
    @Test
    public void encryptRSA() {
        String inputString = "测试abc123";

        byte[] encodedPublicKey = CoreSecurityUtils.decodeBase64(publicKeyThreadLocal.get());
        byte[] encodedPrivateKey = CoreSecurityUtils.decodeBase64(privateKeyThreadLocal.get());

        // 公钥加密
        byte[] encryptResult = CoreSecurityUtils.encryptRSA(inputString.getBytes(), encodedPublicKey);
        System.out.println(CoreSecurityUtils.encodeBase64(encryptResult));

        // 私钥解密
        byte[] decryptResult = CoreSecurityUtils.decryptRSA(encryptResult, encodedPrivateKey);
        Assert.assertTrue(inputString.equals(new String(decryptResult)));
    }

    /**
     * 3DES加密解密测试
     */
    @Test
    public void encryptDESede() {
        String inputString = "测试abc123";

        byte[] encodedSecretKey = CoreSecurityUtils.decodeBase64(desedeKeyThreadLocal.get());

        {
            // ECB加密
            byte[] encryptResult = CoreSecurityUtils.encryptDESede(inputString.getBytes(), encodedSecretKey);
            System.out.println(CoreSecurityUtils.encodeBase64(encryptResult));

            // ECB解密
            byte[] decryptResult = CoreSecurityUtils.decryptDESede(encryptResult, encodedSecretKey);
            Assert.assertTrue(inputString.equals(new String(decryptResult)));
        }

        {
            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = secureRandom.generateSeed(8);

            // CBC加密
            byte[] encryptResult = CoreSecurityUtils.encryptDESede(inputString.getBytes(), encodedSecretKey, iv);
            System.out.println(CoreSecurityUtils.encodeBase64(encryptResult));

            // CBC解密
            byte[] decryptResult = CoreSecurityUtils.decryptDESede(encryptResult, encodedSecretKey, iv);
            Assert.assertTrue(inputString.equals(new String(decryptResult)));
        }
    }

    /**
     * AES加密解密测试
     */
    @Test
    public void encryptAES() {
        String inputString = "测试abc123";

        byte[] encodedSecretKey = CoreSecurityUtils.decodeBase64(aesKeyThreadLocal.get());

        {
            // ECB加密
            byte[] encryptResult = CoreSecurityUtils.encryptAES(inputString.getBytes(), encodedSecretKey);
            System.out.println(CoreSecurityUtils.encodeBase64(encryptResult));

            // ECB解密
            byte[] decryptResult = CoreSecurityUtils.decryptAES(encryptResult, encodedSecretKey);
            Assert.assertTrue(inputString.equals(new String(decryptResult)));
        }

        {
            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = secureRandom.generateSeed(16);

            // CBC加密
            byte[] encryptResult = CoreSecurityUtils.encryptAES(inputString.getBytes(), encodedSecretKey, iv);
            System.out.println(CoreSecurityUtils.encodeBase64(encryptResult));

            // CBC解密
            byte[] decryptResult = CoreSecurityUtils.decryptAES(encryptResult, encodedSecretKey, iv);
            Assert.assertTrue(inputString.equals(new String(decryptResult)));
        }
    }
}
