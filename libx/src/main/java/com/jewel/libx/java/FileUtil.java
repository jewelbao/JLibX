package com.jewel.libx.java;

import android.util.Log;

import com.jewel.libx.TAG;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/2/19
 */
public final class FileUtil {
    /**
     * Create a file, if the directory of the file does not exist, create a superior directory
     *
     * @param filePath file path
     * @return Returning true indicates that the file was created successfully or already exists.
     */
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                boolean mkDirsResult = makeDirs(file.getParent());
                if (!mkDirsResult) {
                    Log.d(TAG.TAG, "create File[" + filePath + "] fail because it's parent dir created failed");
                    return false;
                }
            }
            try {
                boolean createFileResult = file.createNewFile();
                Log.d(TAG.TAG, "create File[" + filePath + "] result " + createFileResult);
                return createFileResult;
            } catch (IOException e) {
                Log.e(TAG.TAG, "create File[" + filePath + "]cause exception : " + e.getLocalizedMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            Log.d(TAG.TAG, "File[" + filePath + "] had exists!");
            return true;
        }
    }

    /**
     * Create a directory, if the directory has a parent directory, create the same
     *
     * @param dirPath Directory path
     * @return Return true to create a successful one.
     */
    public static boolean makeDirs(String dirPath) {
        File folder = new File(dirPath);
        if (!folder.exists()) {
            boolean mkDirsResult = folder.mkdirs();
            Log.d(TAG.TAG, "makeDirs[" + dirPath + "] result " + mkDirsResult);
            return mkDirsResult;
        }
        Log.d(TAG.TAG, "Dirs[" + dirPath + "] had exists!");
        return true;
    }

    /**
     * Delete the file, if the file is a directory and there is a subordinate directory or file, the deletion will fail
     *
     * @param filePath file path
     * @return Returning true indicates that the file was deleted successfully or the file does not exist.
     */
    public static boolean delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleteFileResult = file.delete();
            Log.d(TAG.TAG, "deleted File[" + filePath + "] result " + deleteFileResult);
            return deleteFileResult;
        }
        Log.d(TAG.TAG, "File[" + filePath + "] is not exists");
        return true;
    }


    /**
     * Be cautious! Force the file to be deleted. If the file is a directory and there is a subordinate directory or file, it will be deleted together.
     *
     * @param filePath file path
     * @return Return true to indicate that the file or all files in the file directory are deleted successfully.
     */
    public static boolean deleteForce(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                boolean deleteDirsResult = file.delete();
                Log.d(TAG.TAG, "deleted file[" + filePath + "] result " + deleteDirsResult);
                return deleteDirsResult;
            }
            for (File childFile : childFiles) {
                deleteForce(childFile.getPath());
            }
            boolean deleteDirsResult = file.delete();
            Log.d(TAG.TAG, "deleted file[" + filePath + "] result " + deleteDirsResult);
            return deleteDirsResult;
        }
        Log.d(TAG.TAG, "file[" + filePath + "] is not exists");
        return true;
    }

    /**
     * Rename file
     *
     * @param filePath file path
     * @param newName  New file name
     * @return Returning true indicates that the file was named successfully.
     */
    public static boolean rename(String filePath, String newName) {
        File file = new File(filePath);
        File newFile = new File(file.getParent(), newName);
        boolean renameFileResult = file.renameTo(newFile);
        Log.d(TAG.TAG, "rename File[" + filePath + "] result " + renameFileResult);
        Log.d(TAG.TAG, "newFile Name[" + file.getName() + "] ");
        return renameFileResult;
    }

    /**
     * 文件夹排序
     */
    public static void sortFiles(File[] files) {
        Arrays.sort(files, new Comparator<File>() {

            @Override
            public int compare(File lhs, File rhs) {
                //返回负数表示o1 小于o2，返回0 表示o1和o2相等，返回正数表示o1大于o2。
                boolean l1 = lhs.isDirectory();
                boolean l2 = rhs.isDirectory();
                if (l1 && !l2)
                    return -1;
                else if (!l1 && l2)
                    return 1;
                else {
                    return lhs.getName().compareTo(rhs.getName());
                }
            }
        });
    }

    /**
     * 解压一个压缩文档 到指定位置
     *
     * @param zipFileString 压缩包的名字
     * @param outPathString 指定的路径
     * @throws Exception
     */
    public static void unZipFolder(String zipFileString, String outPathString) throws Exception {
        java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFileString));
        java.util.zip.ZipEntry zipEntry;
        String szName;

        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();

            if (zipEntry.isDirectory()) {

                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                java.io.File folder = new java.io.File(outPathString + java.io.File.separator + szName);
                folder.mkdirs();
            } else {
                java.io.File file = new java.io.File(outPathString + java.io.File.separator + szName);
                file.createNewFile();
                // get the output stream of the file
                java.io.FileOutputStream out = new java.io.FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1) {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }//end of while

        inZip.close();

    }
}
