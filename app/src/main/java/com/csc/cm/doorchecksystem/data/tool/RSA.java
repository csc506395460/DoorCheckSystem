package com.csc.cm.doorchecksystem.data.tool;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by admin on 2017/1/17.
 */

public class RSA {

    public static void main(String[] args) {
        rsa();
    }

    private static String str = "姑娘,我爱你!";// 要加密的内容

    public static void rsa() {
        try {
            // 1.初始化秘钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance("RSA");
            // 设置长度，一定是64整倍数
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            System.out.println("publicKey："
                    + Base64.encodeToString(
                    rsaPublicKey.getEncoded(),Base64.DEFAULT));
            System.out.println("privateKey："
                    + Base64.encodeToString(
                    rsaPrivateKey.getEncoded(),Base64.DEFAULT));
            // 2.私钥加密、 公钥解密--加密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                    rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory
                    .generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(str.getBytes());
            System.out.println("私钥加密、 公钥解密--加密："
                    + Base64.encodeToString(result,Base64.DEFAULT));

            // 3.私钥加密、公钥解密--解密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                    rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal(result);
            System.out.println("私钥加密、公钥解密--解密: " + new String(result));

            // 4.公钥加密、私钥解密--加密
            x509EncodedKeySpec = new X509EncodedKeySpec(
                    rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            result = cipher.doFinal(str.getBytes());
            System.out.println("公钥加密、私钥解密--加密: "
                    + Base64.encodeToString(result,Base64.DEFAULT));

            // 5.公钥加密、私钥解密--解密
            pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                    rsaPrivateKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            result = cipher.doFinal(result);
            System.out.println("公钥加密、私钥解密--解密: " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}