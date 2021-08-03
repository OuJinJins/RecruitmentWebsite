package com.oujiajun.recruitment.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author oujiajun
 * @date 2021/5/21
 */
public class Md5Utils {

    private static final String ENCODING = "UTF-8";

    private Md5Utils() {
    }

    public static String getDigest(String originText) {
        MessageDigest md;
        byte[] digest;
        try {
            md = MessageDigest.getInstance("md5");
            digest = md.digest(originText.getBytes(ENCODING));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("不支持md5加密", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持UTF-8编码格式", e);
        }
        return Base64.getEncoder().encodeToString(digest);
    }
}
