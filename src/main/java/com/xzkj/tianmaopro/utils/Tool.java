package com.xzkj.tianmaopro.utils;

import com.xzkj.tianmaopro.data.CoreData;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.*;

import static org.bukkit.Bukkit.getServer;

public class Tool {
    public static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        CoreData.TMEcecon = (Economy) economyProvider.getProvider();
        return CoreData.TMEcecon != null;
    }

    // 移动文件

    public static void TMFileExists(File file) {

        if (file.exists()) {
            return;
        } else {
            if (file == CoreData.Message) {
                try {
                    File yl = new File(CoreData.ClassPath + "\\Message.yml");
                    copyFileUsingFileStreams(yl, CoreData.Message);
                    Mes.logServer("创建Message完成>>>" + CoreData.Message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (file == CoreData.Mysql) {
                try {
                    File yl = new File(CoreData.ClassPath + "\\mysql.yml");
                    copyFileUsingFileStreams(yl, CoreData.Mysql);
                    Mes.logServer("创建Mysql完成>>>" + CoreData.Message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }


}
