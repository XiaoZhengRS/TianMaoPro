package com.xzkj.tianmaopro.utils;

import com.xzkj.tianmaopro.data.CoreData;
import net.milkbowl.vault.economy.Economy;
import nmslibrary.nmslibrary.util.FileUtils;
import org.bukkit.plugin.RegisteredServiceProvider;


import java.io.File;
import java.io.IOException;

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
            if(file == CoreData.Message){
                try {
                    FileUtils.copyFile(CoreData.ClassPath + "\\Message.yml", String.valueOf(CoreData.Message));
                    Mes.logServer("创建Message完成>>>" + CoreData.Message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(file == CoreData.Mysql){
                try {
                    FileUtils.copyFile(CoreData.ClassPath + "\\mysql.yml", String.valueOf(CoreData.Mysql));
                    Mes.logServer("创建Mysql完成>>>" + CoreData.Message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
