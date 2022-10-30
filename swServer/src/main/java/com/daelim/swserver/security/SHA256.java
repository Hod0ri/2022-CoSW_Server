package com.daelim.swserver.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public String encrypt(String payload) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(payload.getBytes());

        return BytotoHex(md.digest());
    }

    public String BytotoHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {sb.append(String.format("%02x", b));}
        return sb.toString();
    }
}