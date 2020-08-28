package com.nst.yourname.view.ijkplayer.content;

import android.database.AbstractCursor;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PathCursor extends AbstractCursor {
    public static final int CI_FILE_NAME = 1;
    public static final int CI_FILE_PATH = 2;
    public static final int CI_ID = 0;
    public static final int CI_IS_DIRECTORY = 3;
    public static final int CI_IS_VIDEO = 4;
    public static final String CN_FILE_NAME = "file_name";
    public static final String CN_FILE_PATH = "file_path";
    public static final String CN_ID = "_id";
    public static final String CN_IS_DIRECTORY = "is_directory";
    public static final String CN_IS_VIDEO = "is_video";
    public static final String[] columnNames = {CN_ID, CN_FILE_NAME, CN_FILE_PATH, CN_IS_DIRECTORY, CN_IS_VIDEO};
    public static Comparator<FileItem> sComparator = new Comparator<FileItem>() {
        /* class com.nst.yourname.view.ijkplayer.content.PathCursor.AnonymousClass1 */

        public int compare(FileItem fileItem, FileItem fileItem2) {
            if (fileItem.isDirectory && !fileItem2.isDirectory) {
                return -1;
            }
            if (fileItem.isDirectory || !fileItem2.isDirectory) {
                return fileItem.file.getName().compareToIgnoreCase(fileItem2.file.getName());
            }
            return 1;
        }
    };
    public static Set<String> sMediaExtSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
    private List<FileItem> mFileList = new ArrayList();

    public static String apn() {
        return "SVBUViBTbWFydGVycyBQcm8=";
    }

    public double getDouble(int i) {
        return 0.0d;
    }

    public float getFloat(int i) {
        return 0.0f;
    }

    static {
        sMediaExtSet.add("flv");
        sMediaExtSet.add("mp4");
    }

    PathCursor(File file, File[] fileArr) {
        if (file.getParent() != null) {
            FileItem fileItem = new FileItem(new File(file, ".."));
            fileItem.isDirectory = true;
            this.mFileList.add(fileItem);
        }
        if (fileArr != null) {
            for (File file2 : fileArr) {
                this.mFileList.add(new FileItem(file2));
            }
            Collections.sort(this.mFileList, sComparator);
        }
    }

    public int getCount() {
        return this.mFileList.size();
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public String getString(int i) {
        switch (i) {
            case 1:
                return this.mFileList.get(getPosition()).file.getName();
            case 2:
                return this.mFileList.get(getPosition()).file.toString();
            default:
                return null;
        }
    }

    public short getShort(int i) {
        return (short) ((int) getLong(i));
    }

    public int getInt(int i) {
        return (int) getLong(i);
    }

    public long getLong(int i) {
        if (i == 0) {
            return (long) getPosition();
        }
        switch (i) {
            case 3:
                if (this.mFileList.get(getPosition()).isDirectory) {
                    return 1;
                }
                return 0;
            case 4:
                return this.mFileList.get(getPosition()).isVideo ? 1 : 0;
            default:
                return 0;
        }
    }

    public boolean isNull(int i) {
        return this.mFileList == null;
    }

    public static String ukde(String str) {
        byte[] decode = Base64.decode(str, 0);
        if (Build.VERSION.SDK_INT >= 19) {
            return new String(decode, StandardCharsets.UTF_8);
        }
        return "";
    }

    private class FileItem {
        public File file;
        public boolean isDirectory;
        public boolean isVideo;

        public FileItem(PathCursor pathCursor, String str) {
            this(new File(str));
        }

        public FileItem(File file2) {
            int lastIndexOf;
            this.file = file2;
            this.isDirectory = file2.isDirectory();
            String name = file2.getName();
            if (!TextUtils.isEmpty(name) && (lastIndexOf = name.lastIndexOf(46)) >= 0) {
                String substring = name.substring(lastIndexOf + 1);
                if (!TextUtils.isEmpty(substring) && PathCursor.sMediaExtSet.contains(substring)) {
                    this.isVideo = true;
                }
            }
        }
    }
}
