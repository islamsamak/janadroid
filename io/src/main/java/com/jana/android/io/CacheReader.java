/**
 *
 */
package com.jana.android.io;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author isamak
 */
public class CacheReader {

    private Context context;

    public CacheReader(Context context) {
        this.context = context;
    }

    public InputStream readCached(String name) throws FileNotFoundException {

        File parent = context.getExternalCacheDir();

        if (parent == null || !parent.exists()) {
            parent = context.getCacheDir();
        }

        File path = new File(parent, name);

        if (!path.exists()) {
            return null;
        }

        InputStream in = new FileInputStream(path);

        return in;
    }
}
