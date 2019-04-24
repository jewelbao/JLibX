package com.jewel.libx.android;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/4/24
 */
public class ExternalStorageUtil {

    /**
     * 获取存储卡路径以及使用情况
     */
    public void xx() {
        // 获取存储卡路径
        File sdcardDir = Environment.getExternalStorageDirectory();
        // StatFs 看文件系统空间使用情况
        StatFs statFs = new StatFs(sdcardDir.getPath());
        // Block 的 size
        int blockSize = statFs.getBlockSize();
        // 总 Block 数量
        int totalBlocks = statFs.getBlockCount();
        // 已使用的 Block 数量
        int availableBlocks = statFs.getAvailableBlocks();
    }
}
