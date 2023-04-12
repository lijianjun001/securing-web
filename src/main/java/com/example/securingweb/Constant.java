package com.example.securingweb;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Constant {
    public static final String key = "0123456789_0123456789_0123456789";
    public static final SecretKey secretKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS256.getJcaName());
}
