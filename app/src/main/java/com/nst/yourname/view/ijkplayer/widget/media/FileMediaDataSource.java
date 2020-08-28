package com.nst.yourname.view.ijkplayer.widget.media;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import tv.danmaku.ijk.media.player.misc.IMediaDataSource;

public class FileMediaDataSource implements IMediaDataSource {
    private RandomAccessFile mFile;
    private long mFileSize = this.mFile.length();

    public static String apn() {
        return "SnVpY2UgVFYgU21hcnRlcnM=";
    }

    public FileMediaDataSource(File file) throws IOException {
        this.mFile = new RandomAccessFile(file, "r");
    }

    @Override // tv.danmaku.ijk.media.player.misc.IMediaDataSource
    public int readAt(long j, byte[] bArr, int i, int i2) throws IOException {
        if (this.mFile.getFilePointer() != j) {
            this.mFile.seek(j);
        }
        if (i2 == 0) {
            return 0;
        }
        return this.mFile.read(bArr, 0, i2);
    }

    @Override // tv.danmaku.ijk.media.player.misc.IMediaDataSource
    public long getSize() throws IOException {
        return this.mFileSize;
    }

    @Override // tv.danmaku.ijk.media.player.misc.IMediaDataSource
    public void close() throws IOException {
        this.mFileSize = 0;
        this.mFile.close();
        this.mFile = null;
    }
}
