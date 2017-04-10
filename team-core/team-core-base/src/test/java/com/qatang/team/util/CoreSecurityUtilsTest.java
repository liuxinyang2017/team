package com.qatang.team.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 安全工具集合测试类
 *
 * @author qatang
 */
public class CoreSecurityUtilsTest {
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
        System.out.println(result);
        Assert.assertTrue(rightResult == result);
    }
}
