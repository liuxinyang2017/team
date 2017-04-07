package com.qatang.team.algorithm.tpf.spi;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.ServiceLoader;

/**
 * @author qatang
 */
public class SpiMain {
    public static void main(String[] args) throws Exception {
        ServiceLoader<HelloInterface> serviceLoaders = ServiceLoader.load(HelloInterface.class);

        serviceLoaders.forEach(HelloInterface::hello);

        for (Provider provider : Security.getProviders()) {
            System.out.println(provider.getName());
            provider.getServices().forEach(service -> System.out.println("   " + service.getAlgorithm() + "  "+ service.getType()));
        }

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update("aaa".getBytes());
        byte[] result = messageDigest.digest();

        byte[] result2 = messageDigest.digest("aaa".getBytes());

        System.out.println(Arrays.equals(result, result2));
    }
}
