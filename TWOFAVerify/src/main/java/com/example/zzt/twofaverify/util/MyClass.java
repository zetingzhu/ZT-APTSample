package com.example.zzt.twofaverify.util;

import com.example.zzt.twofaverify.util.TotpUtil;

/**
 * @author: zeting
 * @date: 2023/11/22
 */
public class MyClass {
    public static void main(String[] args) {

        // Github
        String secretBase32 = "R66VUVU5K7CWPKIF";
        // Oracle Cloud
//        String secretBase32 = "Z3A27ZY5Z2KHQ6YSP7OCZKKLFU";

        String twoFACode = TotpUtil.generate(secretBase32);

        System.out.println("验证码：" + twoFACode);
    }
}
