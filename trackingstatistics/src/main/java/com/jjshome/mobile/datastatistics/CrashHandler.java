package com.jjshome.mobile.datastatistics;

import android.os.Process;
import com.jjshome.mobile.datastatistics.entity.ErrorInfo;
import com.jjshome.mobile.datastatistics.report.ErrorReport;
import com.jjshome.mobile.datastatistics.report.UploadData;
import com.jjshome.mobile.datastatistics.utils.Common;
import com.jjshome.mobile.datastatistics.utils.DataLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 崩溃信息捕获
 * WQ on 2017/4/10
 * wq@jjshome.com
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private static class SingletonHolder {
        static final CrashHandler instance = new CrashHandler();
    }

    public static CrashHandler getInstance() {
        return SingletonHolder.instance;
    }

    public void init() {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        handleException(ex);
        if (mDefaultCrashHandler != null) {//系统处理
            mDefaultCrashHandler.uncaughtException(t, ex);
        } else {//系统没有处理，我们就结束进程
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 处理异常
     */
    private void handleException(Throwable ex) {
        ErrorInfo errorInfo = new ErrorInfo();

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String errorStack = writer.toString();

        errorInfo.erId = Common.MD5Operation(errorStack);
        errorInfo.type = ErrorInfo.ERR_APP;
        errorInfo.err = errorStack;
        DataLog.d(errorInfo);
        UploadData.upload(new ErrorReport(errorInfo));
    }
}
