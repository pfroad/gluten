package com.gluten.common.hash;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class HmacMD5Sign {

    public static boolean verify(Map<String, Object> parameters, String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String sign = (String) parameters.get("sign");
        return sign(parameters, secretKey).equals(sign) || oldSign(parameters, secretKey).equals(sign);
    }

    public static String sign(Map<String, Object> parameters, String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return HashUtils.hmacMd5(secretKey, createLinkString(signFilter(parameters)), null);
    }

    public static String oldSign(Map<String, Object> parameters, String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return HashUtils.hmacMd5(secretKey, oldCreateLinkString(signFilter(parameters)), null);
    }

    public static Map<String, String> signFilter (Map<String, Object> params) {
        Set<String> keys = params.keySet();

        Map<String, String> result = new HashMap<String, String>();
        for (String key : keys) {
            if (!"sign".equals(key)) {
                String value = params.get(key) == null ? "" : params.get(key).toString();
                result.put(key, value);
            }
        }

        return result;
    }

    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            builder.append(key);
            builder.append("=");
            builder.append(value);

            if (i < keys.size() - 1) {
                builder.append("&");
            }
        }

        return builder.toString();
    }

    public static String oldCreateLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            builder.append(key);
            builder.append("=");
            builder.append(value);

            if (i == keys.size() - 1) {
                builder.append("&");
            }
        }

        return builder.toString();
    }
}
