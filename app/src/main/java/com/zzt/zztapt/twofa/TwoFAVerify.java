package com.zzt.zztapt.twofa;

import static com.zzt.zztapt.twofa.util.TotpUtil.generateTOTP;

import com.zzt.zztapt.twofa.util.Base32String;
import com.zzt.zztapt.twofa.util.HexEncoding;
import com.zzt.zztapt.twofa.util.TotpUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author: zeting
 * @date: 2023/11/22
 */
public class TwoFAVerify {
    private static final String TAG = TwoFAVerify.class.getSimpleName();

    private static class InnerClass {
        private static final TwoFAVerify INSTANCE = new TwoFAVerify();
    }

    private TwoFAVerify() {
    }

    public static TwoFAVerify getInstance() {
        return InnerClass.INSTANCE;
    }

    public String getTwoFACode() {
        String secretBase32 = "R66VUVU5K7CWPKIF";
        String secretHex = "";
        try {
            secretHex = HexEncoding.encode(Base32String.decode(secretBase32));
        } catch (Exception e) {
            e.printStackTrace();
        }

        long X = 30;

        String steps = "0";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        long currentTime = System.currentTimeMillis() / 1000L;
        try {
            long t = currentTime / X;
            steps = Long.toHexString(t).toUpperCase();
            while (steps.length() < 16) steps = "0" + steps;

            return TotpUtil.generateTOTP(secretHex, steps, "6", "HmacSHA1");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
