package com.example.demo.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Map;
import java.util.TreeMap;

public class HMACChecker {
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    public static final String DATA_SEPARATOR = ":";

    /**
     * Проверка подлинности сообщения
     * */
    public static boolean checkHMAC(Map<String, String> map, String secretToken, String hmac) {
        try {
            final String calculatedSign = calculateHMAC(map, secretToken);

            return calculatedSign.equals(hmac);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static String calculateHMAC(Map<String, String> map, String secretToken) throws IllegalArgumentException, SignatureException {
        TreeMap<String, String> treeMap = new TreeMap<>(map);
        StringBuilder data = new StringBuilder();
        for (var key: treeMap.keySet()) {
            data.append(key).append(DATA_SEPARATOR).append(map.get(key)).append(DATA_SEPARATOR);
        }
        return getHmacSha256Algorithm(data.toString(), secretToken);
    }

    /**
     * Calculate the HMAC SHA-256
     * @param data - строка с данными с разделителем
     */
    private static String getHmacSha256Algorithm(String data, String key) throws IllegalArgumentException, SignatureException {
        try {
            if (data == null || key == null) {
                throw new IllegalArgumentException();
            }

            //Создаем подпись
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);

            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);

            //Вычисляем hmac для сообщения
            byte[] macDataBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return new String(Base64.encodeBase64(macDataBytes));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Missing data or key.");
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
    }
}
