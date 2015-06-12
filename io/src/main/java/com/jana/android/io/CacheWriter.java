/**
 *
 */
package com.jana.android.io;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author isamak
 */
public class CacheWriter {

    public CacheWriter() {
    }

    public static File getCacheParent(Context context) {

        File cache = context.getExternalCacheDir();

        if (cache == null || !cache.exists()) {
            cache = context.getCacheDir();
        }

        return cache;
    }

    public File cacheStream(Context context, InputStream in, String name)
            throws IOException {

        File path = getTempFile(context, name);

        cacheStream(context, in, path);

        return path;
    }

    public void cacheStream(Context context, InputStream in, File path)
            throws IOException {

        OutputStream ot = new FileOutputStream(path);

        byte[] buffer = new byte[10240];

        int len = 0;

        while ((len = (in.read(buffer))) > -1) {
            ot.write(buffer, 0, len);
        }

        ot.close();
    }

    public File getTempFile(Context context, String name) throws IOException {

        File cache = getCacheParent(context);

        File file = new File(cache, name);

        file.getParentFile().mkdirs();

        if (!file.exists()) {
            if (!file.createNewFile()) {
                // TODO: Should throw illegal state exception and handle it at caller
            }
        }

        return file;
    }

}
