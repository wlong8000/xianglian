
package com.xianglian.love.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperate {

    /**
     * 拷贝文件
     *
     * @param sourcePath
     * @param targetPath
     * @param delete 是否删除原文件
     * @return
     */
    public static String copyfile(String sourcePath, String targetPath, Boolean delete) {
        File fromFile = new File(sourcePath);
        File toFile = new File(targetPath);
        if (!fromFile.exists()) {
            return null;
        }
        if (!fromFile.isFile()) {
            return null;
        }
        if (!fromFile.canRead()) {
            return null;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c); // 将内容写到新文件当中
            }
            fosfrom.close();
            fosto.close();
        } catch (Exception ex) {
            Log.e("readfile", ex.getMessage());
        }
        return targetPath;
    }

    public static long getFileSizes(File f) {// 取得文件大小
        long s = 0;
        try {
            if (f.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(f);
                f.createNewFile();
                s = fis.available();
            } else {
                Log.e("cn.teamtone", "文件不存在");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (s <= 1024) {
            s = 1;
        } else {
            s = s / 1024;
        }
        return s;
    }
}
