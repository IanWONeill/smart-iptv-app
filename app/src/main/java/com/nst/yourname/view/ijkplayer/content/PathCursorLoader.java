package com.nst.yourname.view.ijkplayer.content;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.content.AsyncTaskLoader;
import java.io.File;

public class PathCursorLoader extends AsyncTaskLoader<Cursor> {
    private File mPath;

    public static String bCBhdXR() {
        return "U";
    }

    public static String pnm() {
        return "Y29tLm5zdC5pcHR2c21hcnRlcnN0dmJveA==";
    }

    public PathCursorLoader(Context context) {
        this(context, Environment.getExternalStorageDirectory());
    }

    public PathCursorLoader(Context context, String str) {
        super(context);
        this.mPath = new File(str).getAbsoluteFile();
    }

    public PathCursorLoader(Context context, File file) {
        super(context);
        this.mPath = file;
    }

    @Override // android.support.v4.content.AsyncTaskLoader
    public Cursor loadInBackground() {
        return new PathCursor(this.mPath, this.mPath.listFiles());
    }

    @Override // android.support.v4.content.Loader
    public void onStartLoading() {
        forceLoad();
    }
}
