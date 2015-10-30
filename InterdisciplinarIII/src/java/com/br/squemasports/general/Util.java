
package com.br.squemasports.general;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public abstract class Util {
    
    public static String reduce(String[] collection) {
        return reduce(collection, ", ");
    }
    public static String reduce(String[] collection, String separator) {
        return reduce(collection, null, separator);
    }
    public static String reduce(String[] collection, Function<? super String, ? extends String> map, String separator) {
        if (collection != null && collection.length > 0) {
            Stream<String> s = Stream.of(collection);
            if (map != null) {
                s = s.map(map);
            }
            return s.reduce("", (t, u) -> t + (!"".equals(t) ? separator : "") + u);
        }
        return "";
    }
    
    public static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            String s = new BigInteger(1, md.digest()).toString(16);
            while (s.length() < 32) s = "0" + s;
            return s;
        } catch (NoSuchAlgorithmException ex) {
            return "";
        }
    }
    
}
