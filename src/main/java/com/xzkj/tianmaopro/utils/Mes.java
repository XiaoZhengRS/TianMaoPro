package com.xzkj.tianmaopro.utils;

import com.xzkj.tianmaopro.data.CoreData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Mes {
    public static void logServer(String str){
        Bukkit.getLogger().info("§d[§5TianMaoPro§d]§7>>>§b " + str);
    }
    public static void logPlayer(String str, Player p){
        p.sendMessage(CoreData.PluginGameName + str);
    }
}
