/*
 * Copyright (C) 2013 OneTeam (IslamSamak : islamsamak01@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jana.android.content;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.jana.android.utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataProvider extends SQLiteOpenHelper {

    protected static DataProvider sInstance;

    protected String mDatabaseName;

    protected int mDatabaseVersion;

    protected SQLiteDatabase mDatabase;

    protected DataProvider(Context context, String databaseName,
                           int databaseVersion) {
        super(context, databaseName, null, databaseVersion);

        this.mDatabaseName = databaseName;
        this.mDatabaseVersion = databaseVersion;
    }

    public void prepareDatabase(Context context, String databaseName) {

        try {

            File destFile = getDestPath(context, databaseName);

            boolean dbExists = destFile.exists();

            if (!dbExists) {

                InputStream srcStream = getSrcStream(context, databaseName);

                boolean copied = copyFile(srcStream, destFile);

                if (!copied) {
                    Logger.e("DataProvider::init[]: Failed to copy database");
                }
            }

        } catch (FileNotFoundException e) {

            Logger.w("DataProvider::prepareDatabase[]: Bad database path");

            e.printStackTrace();
        } catch (NameNotFoundException e) {

            Logger.w("DataProvider::prepareDatabase[]: Bad package name");

            e.printStackTrace();
        } catch (IOException e) {

            Logger.w("DataProvider::prepareDatabase[]: Filed to copy database");

            e.printStackTrace();
        }
    }

    private boolean copyFile(InputStream srcStream, File destFile)
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

    private File getDestPath(Context context, String databaseName)
            throws NameNotFoundException {

        PackageManager pkgMgr = context.getPackageManager();

        String pkgName = context.getPackageName();

        PackageInfo pkgInfo = pkgMgr.getPackageInfo(pkgName, 0);

        File dest = new File(pkgInfo.applicationInfo.dataDir + File.separator
                + "databases" + File.separator + databaseName);

        return dest;
    }

    protected InputStream getSrcStream(Context context, String databaseName)
            throws IOException {

        AssetManager assetMgr = context.getAssets();

        InputStream in = assetMgr.open("database" + File.separator
                + databaseName);

        return in;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // FIXME {Islam@240713}
        // No need to create tables as the content is predefined

        // try {
        // db.execSQL(Tables.Favorite.CREATE);
        // } catch (SQLException e) {
        // Logger.e("couldn't create favorite table");
        // throw e;
        // }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // FIXME {Islam@240713}
        // Find a way to upgrade the tables schema

        // try {
        // db.execSQL(Tables.Favorite.DROP);
        // onCreate(db);
        // } catch (SQLException e) {
        // Logger.e("couldn't upgrade table favorite");
        // throw e;
        // }
    }

    protected Cursor getCursor(final String tableName, final String where,
                               final String[] args, String orderBy, boolean argsIncluded) {

        String sQuery = "SELECT * FROM " + tableName;

        if (!TextUtils.isEmpty(where)) {

            if (argsIncluded) {

                sQuery += " WHERE " + where;

            } else if (args != null && args.length > 0) {
                // TODO: {Islam@280713}
                // Later implement the following
                // - Merge args with where clause

                // Where clause contains the args info
                sQuery += " WHERE " + where + " " + args[0];
            }
        }

        if (!TextUtils.isEmpty(orderBy)) {

            sQuery += " ORDER BY " + orderBy;
        }

        SQLiteDatabase db = getDatabase();

        Cursor cursor = db.rawQuery(sQuery, null);

        return cursor;
    }

    protected Cursor getCursor(final String tableName, final String where,
                               final String[] args, String orderBy) {

        return getCursor(tableName, where, args, orderBy, false);
    }

    protected SQLiteDatabase getDatabase() {

        if (mDatabase == null || !mDatabase.isOpen()) {

            mDatabase = getWritableDatabase();
        }

        return mDatabase;
    }

    @Override
    protected void finalize() throws Throwable {

        super.finalize();

        if (mDatabase != null && mDatabase.isOpen()) {

            mDatabase.close();

            mDatabase = null;
        }
    }

}