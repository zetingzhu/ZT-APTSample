package com.common.lib.util;

import com.common.lib.BuildConfig;

import java.util.Arrays;

/**
 * 注意 调试的时候
 * 华为 荣耀6 红米1s（HM1S）日志 只能显示
 * android.util.Log.e
 * 其他级别不显示
 * <p/>
 * 遇见类似情况都可 提升log级别到Log.e
 */
public class Log {
    public static final String TAG = "Log";
    public static boolean isLog = BuildConfig.DEBUG;
    public static final int LEAVEL = android.util.Log.ERROR;

    /**
     * 开启日志
     *
     * @param log
     */
    public static void setDebug(boolean log) {
        isLog = log;
    }

    public static void v(String TAG, String content) {
        if (isLog) {
            if (LEAVEL == android.util.Log.ERROR)
                android.util.Log.e(TAG, content);
            else
                android.util.Log.v(TAG, content);

        }
    }

    public static void v(String TAG, String content, Throwable tr) {
        if (isLog) {
            if (LEAVEL == android.util.Log.ERROR)
                android.util.Log.e(TAG, content, tr);
            else
                android.util.Log.v(TAG, content, tr);
        }
    }

    public static void d(String TAG, String content) {
        if (isLog) {
            android.util.Log.d(TAG, content);
        }
    }

    public static void d(String TAG, String content, Throwable tr) {
        if (isLog) {
            android.util.Log.d(TAG, content, tr);
        }
    }

    public static void e(String TAG, String content) {
        if (isLog) {
            android.util.Log.e(TAG, content);
        }
    }

    public static void e(String TAG, String content, Throwable tr) {
        if (isLog) {
            android.util.Log.e(TAG, content, tr);
        }
    }

    public static void i(String TAG, String content) {
        if (isLog) {
            android.util.Log.i(TAG, content);
        }
    }

    public static void i(String TAG, String content, Throwable tr) {
        if (isLog) {
            android.util.Log.i(TAG, content, tr);
        }
    }

    public static void w(String TAG, String content) {
        if (isLog) {
            android.util.Log.w(TAG, content);
        }
    }

    public static void w(String TAG, String content, Throwable tr) {
        if (isLog) {
            android.util.Log.w(TAG, content, tr);
        }
    }


    /**
     * 慎用
     * <p>
     * 超长日志打印，解决日志过长丢失
     */
    public void printLongLog(int priority, String tag, String content) {
        if (!isLog) {
            return;
        }
        // 1. 测试控制台最多打印4062个字节，不同情况稍有出入（注意：这里是字节，不是字符！！）
        // 2. 字符串默认字符集编码是utf-8，它是变长编码一个字符用1~4个字节表示
        // 3. 这里字符长度小于1000，即字节长度小于4000，则直接打印，避免执行后续流程，提高性能哈
        if (content.length() < 1000) {
            android.util.Log.println(priority, tag, content);
            return;
        }

        // 一次打印的最大字节数
        int maxByteNum = 4000;

        // 字符串转字节数组
        byte[] bytes = content.getBytes();

        // 超出范围直接打印
        if (maxByteNum >= bytes.length) {
            android.util.Log.println(priority, tag, content);
            return;
        }

        // 分段打印计数
        int count = 1;

        // 在数组范围内，则循环分段
        while (maxByteNum < bytes.length) {
            // 按字节长度截取字符串
            String subStr = cutStr(bytes, maxByteNum);

            // 打印日志
            String desc = String.format("分段打印(%s):%s", count++, subStr);
            android.util.Log.println(priority, tag, desc);

            // 截取出尚未打印字节数组
            bytes = Arrays.copyOfRange(bytes, subStr.getBytes().length, bytes.length);
        }

        // 打印剩余部分
        android.util.Log.println(priority, tag, String.format("分段打印(%s):%s", count, new String(bytes)));
    }


    /**
     * 按字节长度截取字节数组为字符串
     *
     * @param bytes
     * @param subLength
     * @return
     */
    public String cutStr(byte[] bytes, int subLength) {
        // 边界判断
        if (bytes == null || subLength < 1) {
            return null;
        }

        // 超出范围直接返回
        if (subLength >= bytes.length) {
            return new String(bytes);
        }

        // 复制出定长字节数组，转为字符串
        String subStr = new String(Arrays.copyOf(bytes, subLength));

        // 避免末尾字符是被拆分的，这里减1使字符串保持完整
        return subStr.substring(0, subStr.length() - 1);
    }
}
