package com.qatang.team.util;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

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
}
