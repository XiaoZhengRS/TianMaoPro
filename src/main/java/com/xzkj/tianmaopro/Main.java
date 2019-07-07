package com.xzkj.tianmaopro;

import com.xzkj.tianmaopro.api.Papi;
import com.xzkj.tianmaopro.data.CoreData;
import com.xzkj.tianmaopro.listener.TMListener;
import com.xzkj.tianmaopro.utils.ConfigList;
import com.xzkj.tianmaopro.utils.Mes;
import com.xzkj.tianmaopro.utils.Tool;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Set;

public final class Main extends JavaPlugin {
    @Override
    public void onLoad() {
        saveDefaultConfig();
        //创建Default配置
        CoreData.ClassPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    @Override
    public void onEnable() {
        Mes.logServer("======TianMaoPro======");
        Mes.logServer("Hello, 我是你们的小可爱!");
        Mes.logServer("下面开始为您预加载本插件!");
        CoreData.PluginPath = new File("./plugins", "\\TianMaoPro");
        Mes.logServer("设置Plugin路径完成>>>" + CoreData.PluginPath);
        CoreData.PlayerShop = new File("./plugins/TianMaoPro", "\\PlayerShop");
        CoreData.PlayerShop.mkdirs();
        Mes.logServer("设置PlayerShop路径完成>>>" + CoreData.PlayerShop);
        CoreData.Config = getConfig();
        Mes.logServer("设置Config完成>>>" + CoreData.Config);
        Plugin placeholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        Plugin playerPoints = Bukkit.getPluginManager().getPlugin("PlayerPoints");
        Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
        Plugin NMS = Bukkit.getPluginManager().getPlugin("NMSLibrary");
        if (placeholderAPI == null) {
            Mes.logServer("§4(未发现)PlaceholderAPI");
        } else {
            PlaceholderAPI.registerPlaceholderHook("TM", new Papi());
            Mes.logServer("§2(发现)PlaceholderAPI" + "§3>>>§d[§9完成Hook§d]");
            Mes.logServer("■■■■■■■■■■■■■■■■成功注册以下变量■■■■■■■■■■■■■■■■");
            Mes.logServer("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
            Mes.logServer("★%TM_PlayerTianMaoName% -> 玩家店铺名称");
            Mes.logServer("★%TM_PlayerTianMaoNumberOfProducts% -> 玩家店铺商品数量");
            Mes.logServer("★%TM_PlayerTianMaoSalesVolume% -> 玩家店铺销量总价");
            Mes.logServer("★%TM_PlayerTianMaoLevel% -> 玩家店铺等级");
            Mes.logServer("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        }
        if (playerPoints == null) {
            Mes.logServer("§4(未发现)playerPoints");
        } else {
            Mes.logServer("§2(发现)playerPoints" + "§3>>>§d[§9完成Hook§d]");
        }
        if (vault == null) {
            Mes.logServer("§4(未发现)vault");
        } else {
            Tool.setupEconomy();
            Mes.logServer("§2(发现)vault" + "§3>>>§d[§9完成Hook§d]");
            Mes.logServer("注册Economy完成!");
        }
        if (NMS == null) {
            Mes.logServer("§4(未发现)NMSLibrary核心前置!");
        } else {
            Mes.logServer("§2(发现)NMSLibrary核心前置!" + "§3>>>§d[§9完成Hook§d]");
        }
        try {
            CoreData.ClassPath = java.net.URLDecoder.decode(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            Mes.logServer("设置Class完成>>>" + CoreData.ClassPath);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CoreData.Message = new File("./plugins/TianMaoPro", "\\Message.yml");
        CoreData.Mysql = new File("./plugins/TianMaoPro", "\\mysql.yml");
        Tool.TMFileExists(CoreData.Message);
        Mes.logServer("设置Message完成>>>" + CoreData.Message);
        Tool.TMFileExists(CoreData.Mysql);
        Mes.logServer("设置Mysql完成>>>" + CoreData.Mysql);

        CoreData.TM_Create = CoreData.Config.getInt("天猫配置.创建店铺所需手续费");
        CoreData.TM_Change_Name = CoreData.Config.getInt("天猫配置.更改店铺名称手续费");
        CoreData.TM_Shop_Name_NO = CoreData.Config.getStringList("天猫配置.店铺名称中不可使用的字符");
        YamlConfiguration message = YamlConfiguration.loadConfiguration(CoreData.Message);
        CoreData.TM_Help = message.getStringList("help");
        Set<String> TM_Shop_Name = getConfig().getConfigurationSection("等级").getKeys(false);
        for (String str : TM_Shop_Name) {
            CoreData.TM_Shop_Name.add(str);
        }
        for (int i = 0; i < TM_Shop_Name.size(); i++) {
            int Shop_Tax = CoreData.Config.getInt("等级." + CoreData.TM_Shop_Name.get(i) + ".税收");
            int Shop_Max_Commodity = CoreData.Config.getInt("等级." + CoreData.TM_Shop_Name.get(i) + ".最高上架商品种类");
            int Shop_Integral = CoreData.Config.getInt("等级." + CoreData.TM_Shop_Name.get(i) + ".激活积分");
            Boolean Shop_Ticket_Mode = CoreData.Config.getBoolean("等级." + CoreData.TM_Shop_Name.get(i) + ".是否开启点券交易模式");
            CoreData.TM_Shop_Tax.add(Shop_Tax);
            CoreData.TM_Shop_Max_Commodity.add(Shop_Max_Commodity);
            CoreData.TM_Shop_Integral.add(Shop_Integral);
            CoreData.TM_Shop_Ticket_Mode.add(Shop_Ticket_Mode);
        }
        ConfigList.TMlist();
        this.getCommand("TM").setExecutor(new Cmd());
        Mes.logServer("绑定TM命令完成!");
        Bukkit.getPluginManager().registerEvents(new TMListener(), this);
        Mes.logServer("注册TM监听完成!");
        YamlConfiguration v = YamlConfiguration.loadConfiguration(CoreData.Message);
        CoreData.PluginGameName = v.getString("PluginGameName");
        Mes.logServer("|TianMaoPro|作者QQ:1419158026");
        Mes.logServer("======TianMaoPro======");
    }

    @Override
    public void onDisable() {
        Mes.logServer("==========TianMaoPro==========");
        Mes.logServer("|TianMaoPro|作者QQ:1419158026");
        Mes.logServer("==========TianMaoPro==========");
    }
}
