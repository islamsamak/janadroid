/**
 *
 */
package com.jana.android.io;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Islam Samak
 */
public class CopyFile {

    public static boolean copyFile(InputStream srcStream, File destFile)
            throws IOException {

        File parent = destFile.getParentFile();

        parent.mkdirs();

        OutputStream outStream = new FileOutputStream(destFile);

        byte[] buffer = new byte[102400];

        int length;

        while ((length = srcStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);
        }

        outStream.flush();

        outStream.close();

        srcStream.close();

        return true;
    }

    public static File getDatabasePath(Context context, String databaseName)
            throws NameNotFoundException {

        PackageManager pkgMgr = context.getPackageManager();

        String pkgName = context.getPackageName();

        PackageInfo pkgInfo = pkgMgr.getPackageInfo(pkgName, 0);

        File file = new File(pkgInfo.applicationInfo.dataDir + File.separator
                + "databases" + File.separator + databaseName);

        return file;
    }

    public static InputStream readDatabaseStream(Context context,
                                                 String databaseName) throws IOException {

        AssetManager assetMgr = context.getAssets();

        InputStream in = assetMgr.open("database" + File.separator
                + databaseName);

        return in;
    }

}
