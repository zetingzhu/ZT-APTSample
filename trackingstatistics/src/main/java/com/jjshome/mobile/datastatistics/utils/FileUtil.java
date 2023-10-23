package com.jjshome.mobile.datastatistics.utils;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();
    private static final String BITMAP_PREFIX = "bitmap";
    private static final String FILE_NAME_TEMPLATE = "%s_%s.jpg";
    /**
     * Writes the bitmap the directory, creating the directory if it doesn't exist.
     */
    @Nullable
    @WorkerThread
   public static File writeBitmapToDirectory(@NonNull Bitmap bitmap, @NonNull File directory) {
        if (!directory.mkdirs() && !directory.exists()) {
            return null;
        }
        return writeBitmapToFile(bitmap, new File(directory, createUniqueFilename(BITMAP_PREFIX)));
    }

    /**
     * Create a unique file name starting with the prefix.
     */
    @NonNull
    public static String createUniqueFilename(String prefix) {
        String randomId = Long.toString(System.currentTimeMillis());
        return String.format(Locale.US, FILE_NAME_TEMPLATE, prefix, randomId);
    }

    /**
     * Writes the bitmap to disk and returns the new file.
     *
     * @param bitmap Bitmap the bitmap to write
     * @param file   the file to write to
     */
    @Nullable
    @WorkerThread
    public static File writeBitmapToFile(@NonNull Bitmap bitmap, @NonNull File file) {
        FileOutputStream fileStream = null;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
            fileStream = new FileOutputStream(file);
            fileStream.write(byteStream.toByteArray());
            return file;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
        return null;
    }

}
