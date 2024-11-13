package com.nisanth.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class OtpUtil {
   private static String code;
    public static String generateOtp()
    {

        SecureRandom random= null;
        try {
            random = SecureRandom.getInstanceStrong();
            int initRandom= random.nextInt(8999)+1000;
            // 823+1000=1823

            code=String.valueOf(initRandom);
        }
        catch (NoSuchAlgorithmException e)
        {

            throw new RuntimeException(e);
        }
        return code;

    }
}
